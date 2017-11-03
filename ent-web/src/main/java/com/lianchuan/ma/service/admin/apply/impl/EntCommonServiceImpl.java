package com.lianchuan.ma.service.admin.apply.impl;

import com.lianchuan.common.entity.po.ent.EntApplyOperateLogPO;
import com.lianchuan.common.entity.po.ent.EntApplyPO;
import com.lianchuan.common.entity.po.ent.EntUploadInfoPO;
import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.type.ent.ApplyStatus;
import com.lianchuan.common.entity.type.ent.OperateType;
import com.lianchuan.common.entity.type.ent.UploadFileType;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.entity.vo.apply.ApplyParam;
import com.lianchuan.common.exception.ParamException;
import com.lianchuan.common.oss.OssBizService;
import com.lianchuan.common.service.dao.ent.EntCommonDaoService;
import com.lianchuan.common.utils.ServletUtils;
import com.lianchuan.ma.Global;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.apply.*;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.*;
import com.lianchuan.ma.entity.vo.result.system.UserVO;
import com.lianchuan.ma.service.AbstractMaService;
import com.lianchuan.ma.service.admin.apply.EntCommonService;
import com.lianchuan.ma.service.admin.push.PushRenzhongService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("entCommonService")
public class EntCommonServiceImpl extends AbstractMaService implements EntCommonService {

	@Autowired
	private OssBizService ossBizService;

	@Autowired
	protected EntCommonDaoService entCommonDaoService;

	@Autowired
	protected PushRenzhongService pushRenzhongService;

	@Override
	public GetApplyInfoListResult getEntInfoList(GetApplyInfoListParam param, Menu menu, List<Integer> applyStatusList,
			String orderBy) {
		getManager(param, menu);

		ApplyParam applyParam = convert(param);
		Page<EntApplyPO> page = entCommonDaoService.findEntApply(applyParam, applyStatusList, orderBy);
		GetApplyInfoListResult result = new GetApplyInfoListResult();

		if (CollectionUtils.isNotEmpty(page.getInfo())) {
			List<ApplyInfoVO> applyInfos = new ArrayList<ApplyInfoVO>();
			for (EntApplyPO po : page.getInfo()) {
				ApplyInfoVO vo = new ApplyInfoVO();
				vo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(po.getCreateDate()));
				vo.setId(po.getId());

				MaManagerPO maManager = entCommonDaoService.findManagerById(po.getCreateManagerId());
				if(maManager !=null){
					vo.setCreateManagerName(maManager.getName());
				}
				vo.setCode(po.getCode());
				vo.setLoanMoney(po.getLoanMoney());
				vo.setName(po.getName());
				vo.setRegistMoney(po.getRegistMoney());
				vo.setAddress(po.getAddress());
				vo.setFoundDate(new SimpleDateFormat("yyyy-MM-dd").format(po.getFoundDate()));
				vo.setLegalRepresentative(po.getLegalRepresentative());
				vo.setLoanUsage(po.getLoanUsage());
				vo.setApplyStatusDesc(ApplyStatus.getType(po.getApplyStatus()).getName());
				vo.setApplyStatus(po.getApplyStatus());

				List<EntUploadInfoPO> entUploadInfoList = entCommonDaoService.findEntUploadInfoByApplyId(po.getId());

				List<ContractInfoVO> list = new ArrayList<ContractInfoVO>();
				for (EntUploadInfoPO epo : entUploadInfoList) {
					ContractInfoVO cvo = new ContractInfoVO();
					cvo.setContractName(epo.getFileName());
					cvo.setFileId(epo.getId());
//					cvo.setContractFileType(epo.getContractFileType());
					// 合同链接地址
					list.add(cvo);
				}
				vo.setContractInfoList(list);

				applyInfos.add(vo);
			}
			result.setInfos(applyInfos);
		}
		setPageArgument(result, page);
		return result;
	}

	/**
	 * 视图层查询条件转换业务层
	 *
	 * @param param
	 * @return
	 */
	private ApplyParam convert(GetApplyInfoListParam param) {

		checkPage(param);

		ApplyParam applyParam = new ApplyParam();
		applyParam.setPageNo(param.getPageNo());
		applyParam.setPageSize(param.getPageSize());

		if (StringUtils.isNotBlank(param.getStartTime())) {
			try {
				applyParam.setStartTime(DateUtils.parseDate(param.getStartTime(), "yyyy-MM-dd"));
			} catch (Exception e) {
				throw new ParamException("开始时间格式错误");
			}
		}

		if (StringUtils.isNotBlank(param.getEndTime())) {
			try {
				Date endt = DateUtils.parseDate(param.getEndTime(), "yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(endt);
				cal.add(Calendar.DAY_OF_YEAR, +1);
				Date endTime = new SimpleDateFormat("yyyy-MM-dd")
						.parse(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
				applyParam.setEndTime(endTime);
			} catch (Exception e) {
				throw new ParamException("结束时间格式错误");
			}
		}

		if (applyParam.getStartTime() != null && applyParam.getEndTime() != null && //
				applyParam.getEndTime().getTime() < applyParam.getStartTime().getTime()) {
			throw new ParamException("开始时间不能大于结束时间");
		}

		if (param.getCreateManagerId() != null) {
			applyParam.setCreateManagerId(param.getCreateManagerId());
		}

		if (param.getApplyStatus() != null) {
			ApplyStatus applyStatus = ApplyStatus.getType(param.getApplyStatus());
			if (applyStatus == null) {
				throw new ParamException("申请状态异常");
			}
			applyParam.setApplyStatus(param.getApplyStatus());
		}
		return applyParam;
	}

	@Override
	public GetEntApplyDetailResult getEntDetail(GetEntApplyDetailParam param, Menu menu) {
		getManager(param, menu);
		checkObject(param.getId());
		EntApplyPO entApplyPO = entCommonDaoService.findEntApplyById(param.getId());
		GetEntApplyDetailResult result = new GetEntApplyDetailResult();
		if (entApplyPO != null) {
			result.setId(entApplyPO.getId());
			result.setName(entApplyPO.getName());
			result.setRegistMoney(entApplyPO.getRegistMoney());
			result.setAddress(entApplyPO.getAddress());
			result.setFoundDate(entApplyPO.getFoundDate());
			result.setLegalRepresentative(entApplyPO.getLegalRepresentative());
			result.setLoanUsage(entApplyPO.getLoanUsage());
			result.setLoanMoney(entApplyPO.getLoanMoney());
			result.setContactPhone(entApplyPO.getContactPhone());
			result.setSocialCreditIdentifier(entApplyPO.getSocialCreditIdentifier());
			result.setBusinessRegistrationNo(entApplyPO.getBusinessRegistrationNo());

			result.setLoanDate(entApplyPO.getLoanDate());
			result.setLoanPeriod(entApplyPO.getLoanPeriod());
			result.setBorrowStyle(entApplyPO.getBorrowStyle());
			result.setBankName(entApplyPO.getBankName());
			result.setBankAccountName(entApplyPO.getBankAccountName());
			result.setBankNo(entApplyPO.getBankNo());
			result.setContractNo(entApplyPO.getContractNo());
		}
		List<EntApplyOperateLogPO> operateLogList = entCommonDaoService.findEntApplyOperatorLogById(param.getId());
		if (operateLogList != null && operateLogList.size() > 0) {
			List<ApplyOperateLogVO> list = new ArrayList<ApplyOperateLogVO>();
			for (EntApplyOperateLogPO po : operateLogList) {
				ApplyOperateLogVO vo = new ApplyOperateLogVO();

				vo.setAuditDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(po.getAuditDate()));
				vo.setRemark(po.getRemark());

				vo.setOperateTypeName((OperateType.getType(po.getOperateType()).getName()));
				MaManagerPO maManager = entCommonDaoService.findManagerById(po.getAuditManagerId());
				vo.setAuditName(maManager.getName());
				list.add(vo);
			}
			result.setOperatorLogList(list);
		}
		List<EntUploadInfoPO> uploadInfoList = entCommonDaoService.findEntUploadInfoByApplyId(param.getId());
		if (uploadInfoList != null && uploadInfoList.size() > 0) {
//			HashMap<Integer, List<ContractInfoVO>> contractMap = new HashMap<Integer, List<ContractInfoVO>>();
			List<ContractInfoVO> contractInfoList = new ArrayList<ContractInfoVO>();
			for (EntUploadInfoPO po : uploadInfoList) {
				ContractInfoVO vo = new ContractInfoVO();
//				vo.setContractFileType(po.getContractFileType());
				vo.setContractName(po.getFileName());
				vo.setFileId(po.getId());
				contractInfoList.add(vo);
			}
//			result.setContractInfoMap(contractMap);
			result.setContractInfoList(contractInfoList);
		}
		return result;
	}

	@Override
	public BaseMaResult entJudge(RiskJudgeParam param) {
		getManager(param, Menu.M_120100);
		checkObject(param.getId());
		checkObject(param.getApplyStatus());
		if (param.getApplyStatus() == ApplyStatus.AUDIT_FAIL.getValue()) {
			checkString(param.getRemark());

		}
		if (!(param.getApplyStatus() == ApplyStatus.AUDIT_FAIL.getValue()
				|| param.getApplyStatus() == ApplyStatus.AUDIT_PASS.getValue())) {
			throw new ParamException("参数状态异常");
		}
		EntApplyPO entApplyPO = entCommonDaoService.findEntApplyById(param.getId());
		if (entApplyPO == null) {
			throw new ParamException("申请信息不存在！");
		}
		entCommonDaoService.updateApplyStatus(entApplyPO, param.getApplyStatus());

		entCommonDaoService.saveEntApplyOperateLog(entApplyPO.getId(), param.getApplyStatus(), param.getRemark(),
				param.getOperatorId());
		BaseMaResult result = new BaseMaResult();
		return result;
	}

	@Override
	public GetAllManagerResult getAllUser(BaseOperatorParam param) {
		getManager(param, new Menu[] {});
		GetAllManagerResult result = new GetAllManagerResult();
		List<MaManagerPO> managerList = entCommonDaoService.findAllManager();
		if (managerList != null && managerList.size() > 0) {
			List<UserVO> list = new ArrayList<UserVO>();
			for (MaManagerPO po : managerList) {
				UserVO vo = new UserVO();
				vo.setName(po.getName());
				vo.setId(po.getId());
				list.add(vo);
			}
			result.setList(list);
		}
		return result;
	}

	@Override
	public BaseMaResult downloadFile(FileDownloadParam param, HttpServletRequest request, HttpServletResponse response) {
		getManager(param, new Menu[] {});
		checkObject(param.getFileId());
		EntUploadInfoPO fileInfo = entCommonDaoService.findEntUploadInfoById(param.getFileId());
		byte[] data = null;
		try {
			data = ossBizService.getContractFile(fileInfo.getFilePath());
		} catch (Exception e) {
			throw new ParamException("文件读取错误！" + e.getMessage());
		}
		if (data == null) {
			throw new ParamException("文件不存在！");
		}
		UploadFileType fileType = null;
		if (fileInfo.getFileType() != null) {
			fileType = UploadFileType.getType(fileInfo.getFileType());
		}
		String contentType = Global.CONTENT_TYPE;
		if (fileType != null) {
			contentType = fileType.getContentType();
		}
		ServletUtils.downloadFile(data, fileInfo.getFileName(), contentType, request, response);
		return new BaseMaResult();
	}

	@Override
	public BaseMaResult downloadEntFile(Long applyId, HttpServletRequest request, HttpServletResponse response) {
		checkObject(applyId);
		EntApplyPO applyPO = entCommonDaoService.findEntApplyById(applyId);
		DateFormat yyyyFormat = new SimpleDateFormat("yyyy");
		DateFormat mmFormat = new SimpleDateFormat("MM");
		DateFormat ddFormat = new SimpleDateFormat("dd");
		Date createDate = applyPO.getCreateDate();
		String year = yyyyFormat.format(createDate);
		String month = mmFormat.format(createDate);
		String day = ddFormat.format(createDate);
		byte[] data = null;
		try {
			data = ossBizService.getZipFile(year, month, day, applyId);
		} catch (Exception e) {
			throw new ParamException("文件读取错误！" + e.getMessage());
		}
		if (data == null) {
			throw new ParamException("文件不存在！");
		}
		String contentType = Global.CONTENT_TYPE;
		ServletUtils.downloadFile(data, applyPO.getName() + ".zip", contentType, request, response);
		return new BaseMaResult();
	}

	@Override
	public BaseMaResult entPush(ReviewApplyInfoParam param) {
		getManager(param, Menu.M_130100);
		checkObject(param.getId());
		EntApplyPO entApplyPO = entCommonDaoService.findEntApplyById(param.getId());
		if (entApplyPO == null) {
			throw new ParamException("该标的信息不存在！");
		}
		if (entApplyPO.getApplyStatus() == null || !(entApplyPO.getApplyStatus() == ApplyStatus.PUSH_FAIL.getValue()
				|| entApplyPO.getApplyStatus() == ApplyStatus.AUDIT_PASS.getValue())) {
			throw new ParamException("该标的状态已经变更，请刷新页面！");
		}

		Pair<Boolean, String> res = Pair.of(true, "");
				//pushRenzhongService.pushData(param.getId());
		if (res.getLeft()) {
			entCommonDaoService.updateApplyStatus(entApplyPO, OperateType.PUSH_SUCCESS.getValue());
			entCommonDaoService.saveEntApplyOperateLog(entApplyPO.getId(), OperateType.PUSH_SUCCESS.getValue(), null,
					entApplyPO.getCreateManagerId());
		} else {
			entCommonDaoService.updateApplyStatus(entApplyPO, OperateType.PUSH_FAIL.getValue());
			entCommonDaoService.saveEntApplyOperateLog(entApplyPO.getId(), OperateType.PUSH_FAIL.getValue(), null,
					entApplyPO.getCreateManagerId());
		}

		BaseMaResult result = new BaseMaResult();
		return result;
	}

	@Override
	public BaseMaResult entPushList(ReviewApplyInfoListParam param) {
		getManager(param, Menu.M_130100);
		checkList(param.getIdList());
		for (Long applyId : param.getIdList()) {
			EntApplyPO entApplyPO = entCommonDaoService.findEntApplyById(applyId);
			if (entApplyPO == null) {
				throw new ParamException("审核信息不存在！");
			}
			if (entApplyPO.getApplyStatus() == null || !(entApplyPO.getApplyStatus() == ApplyStatus.PUSH_FAIL.getValue()
					|| entApplyPO.getApplyStatus() == ApplyStatus.AUDIT_PASS.getValue())) {
				throw new ParamException(entApplyPO.getName() +" 的状态已经变更，请刷新页面！");
			}
			Pair<Boolean, String> res = Pair.of(true, "");
					//pushRenzhongService.pushData(applyId);
			if (res.getLeft()) {
				entCommonDaoService.updateApplyStatus(entApplyPO, OperateType.PUSH_SUCCESS.getValue());
				entCommonDaoService.saveEntApplyOperateLog(entApplyPO.getId(), OperateType.PUSH_SUCCESS.getValue(),
						null, entApplyPO.getCreateManagerId());
			} else {
				entCommonDaoService.updateApplyStatus(entApplyPO, OperateType.PUSH_FAIL.getValue());
				entCommonDaoService.saveEntApplyOperateLog(entApplyPO.getId(), OperateType.PUSH_FAIL.getValue(), null,
						entApplyPO.getCreateManagerId());
			}
		}
		BaseMaResult result = new BaseMaResult();
		return result;
	}

}
