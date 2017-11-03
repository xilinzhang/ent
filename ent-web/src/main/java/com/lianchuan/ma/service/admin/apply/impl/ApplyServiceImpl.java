package com.lianchuan.ma.service.admin.apply.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lianchuan.common.entity.type.ent.BorrowStyleType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lianchuan.common.entity.po.ent.EntApplyInfoProvidePO;
import com.lianchuan.common.entity.po.ent.EntApplyPO;
import com.lianchuan.common.entity.po.ent.EntUploadInfoPO;
import com.lianchuan.common.entity.type.ent.OperateType;
import com.lianchuan.common.entity.type.ent.UploadFileType;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.exception.ParamException;
import com.lianchuan.common.oss.OssBizService;
import com.lianchuan.common.service.dao.ent.EntCommonDaoService;
import com.lianchuan.ma.entity.vo.param.apply.DelContractInfoParam;
import com.lianchuan.ma.entity.vo.param.apply.EditApplyInfoParam;
import com.lianchuan.ma.entity.vo.param.apply.EditContractInfoParam;
import com.lianchuan.ma.entity.vo.param.apply.GetApplyInfoProvideParam;
import com.lianchuan.ma.entity.vo.param.apply.ReviewApplyInfoParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.EditApplyInfoResult;
import com.lianchuan.ma.entity.vo.result.apply.EditContractInfoResult;
import com.lianchuan.ma.entity.vo.result.apply.GetApplyInfoProvideResult;
import com.lianchuan.ma.service.AbstractMaService;
import com.lianchuan.ma.service.admin.apply.ApplyService;

/**
 * Created by zxl on 2017-10-23
 **/
@Service("applyService")
public class ApplyServiceImpl extends AbstractMaService implements ApplyService {


    private static Long APPLY_ID_CONSTANT = -1L;

    @Autowired
    protected EntCommonDaoService entCommonDaoService;
    @Autowired
    private OssBizService ossBizService;

    @Override
    public BaseMaResult reviewApplyInfo(ReviewApplyInfoParam param) {
        getManager(param, Menu.M_110100);
        checkObject(param.getId());
        EntApplyPO entApplyPO = entCommonDaoService.findEntApplyById(param.getId());
        if (entApplyPO == null) {
            throw new ParamException("审核信息不存在！");
        }
        List<EntUploadInfoPO> entUploadInfoPOList = entCommonDaoService.findEntUploadInfoByApplyId(param.getId());
        if (CollectionUtils.isEmpty(entUploadInfoPOList)) {
            throw new ParamException("审核标的合同文件不能为空！");
        }
        entCommonDaoService.updateApplyStatus(entApplyPO, OperateType.WAIT_AUDIT.getValue());
        entCommonDaoService.saveEntApplyOperateLog(entApplyPO.getId(), OperateType.WAIT_AUDIT.getValue(), null, entApplyPO.getCreateManagerId());

        BaseMaResult result = new BaseMaResult();
        return result;
    }

    @Override
    public EditApplyInfoResult editApplyInfo(EditApplyInfoParam param) {
        getManager(param, Menu.M_110100);
        validateParam(param);
        if (!isNumber(param.getLoanPeriod() + "")) {
            throw new ParamException("还款期限参数传入不对");
        }
        if (BorrowStyleType.getType(param.getBorrowStyle()) == null) {
            throw new ParamException("还款方式参数传入不对");
        }
        if (!checkNumber(param.getLoanMoney())) {
            throw new ParamException("借款金额参数传入不对");
        }
        if (!checkNumber(param.getRegistMoney())) {
            throw new ParamException("注册资金参数传入不对");
        }

        Long applyId = null;
        if (param.getApplyStatus() != OperateType.NEW_1.getValue() && param.getApplyStatus() != OperateType.WAIT_AUDIT.getValue()) {
            throw new ParamException("状态参数传入不对");
        }
        List<Long> contractIdList = param.getContractIdList();
        if (param.getApplyStatus() == OperateType.WAIT_AUDIT.getValue()) {
            boolean flag = false;
            if (CollectionUtils.isNotEmpty(contractIdList)) {
                for (Long contractId : contractIdList) {
                    EntUploadInfoPO entUploadInfoPO = entCommonDaoService.findEntUploadInfoById(contractId);
                    if (entUploadInfoPO != null) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                throw new ParamException("提交审核标的合同文件不能为空");
            }
        }

        EntApplyPO po = convert(param);

        if (param.getId() == null) {
            // 新增
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String now = sdf.format(new Date());
            try {
                String code = null;
                EntApplyPO lastPo = entCommonDaoService.findMaxCodeByCreateDate(sdf.parse(now));
                if (lastPo != null && StringUtils.isNotBlank(lastPo.getCode())) {
                    Integer num = Integer.parseInt(lastPo.getCode().substring(8, lastPo.getCode().length())) + 1;
                    code = now.substring(0, 4) + now.substring(5, 7) + now.substring(8, 10) + num;
                } else {
                    code = now.substring(0, 4) + now.substring(5, 7) + now.substring(8, 10) + "1000";
                }
                po.setCode(code);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            po.setModifyDate(new Date());
            EntApplyPO newPo = entCommonDaoService.saveEntApplyInfo(po);
            applyId = newPo.getId();
            // 操作日志
            entCommonDaoService.saveEntApplyOperateLog(newPo.getId(), param.getApplyStatus(), null, newPo.getCreateManagerId());
        } else {
            // 修改
            EntApplyPO oldPo = entCommonDaoService.findEntApplyById(param.getId());
            if (oldPo == null) {
                throw new ParamException("编辑信息不存在！");
            }
            po.setCode(oldPo.getCode());
            po.setId(param.getId());
            po.setModifyDate(new Date());
            po.setCreateDate(oldPo.getCreateDate());
            EntApplyPO newPo = entCommonDaoService.saveEntApplyInfo(po);
            applyId = newPo.getId();
            if (oldPo.getApplyStatus() != param.getApplyStatus()) {
                // 操作日志
                entCommonDaoService.saveEntApplyOperateLog(newPo.getId(), param.getApplyStatus(), null, newPo.getCreateManagerId());
            }

        }
        //更新合同文件applyId
        for (Long contractId : contractIdList) {
            EntUploadInfoPO entUploadInfoPO = entCommonDaoService.findEntUploadInfoById(contractId);
            if (entUploadInfoPO != null) {
                entUploadInfoPO.setApplyId(applyId);
                entCommonDaoService.saveEntUploadInfo(entUploadInfoPO);
            }
        }

        EditApplyInfoResult result = new EditApplyInfoResult();
        result.setId(applyId);
        return result;
    }

    @Override
    public EditContractInfoResult editContractInfo(EditContractInfoParam param) {
        getManager(param, Menu.M_110100);
//        checkObject(param.getContractFileType());

        EntUploadInfoPO po = new EntUploadInfoPO();
        po.setApplyId(APPLY_ID_CONSTANT);
//        po.setContractFileType(param.getContractFileType());
        MultipartFile file = param.getMultipartFile();
        if (file == null) {
            throw new ParamException("上传文件不能为空！");
        }
        po.setFileName(file.getOriginalFilename());
        po.setFileSize(file.getSize());
        po.setUploadManagerId(param.getOperatorId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String filePath = null;
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
        String kzm = ext.substring(1, ext.length());
        int fileType = UploadFileType.getValue(kzm);
//        if (fileType == 0) {
//            throw new ParamException("文件格式不合法！");
//        }
        po.setFileType(fileType);
        try {
            filePath = ossBizService.uploadContractFile(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1
                    , calendar.get(Calendar.DAY_OF_MONTH), ext, file.getBytes());
        } catch (IOException e) {
            throw new ParamException("上传文件异常！");
        }
        po.setFilePath(filePath);

        if (param.getContractId() != null) {
            po.setId(param.getContractId());
        }
        EntUploadInfoPO retp = entCommonDaoService.saveEntUploadInfo(po);
        EditContractInfoResult result = new EditContractInfoResult();
        result.setContractId(retp.getId());
//        result.setContractFileType(retp.getContractFileType());
        result.setFileName(retp.getFileName());
        return result;
    }

    @Override
    public BaseMaResult delContractInfo(DelContractInfoParam param) {
        getManager(param, Menu.M_110100);
        checkObject(param.getId());
        EntUploadInfoPO po = entCommonDaoService.findEntUploadInfoById(param.getId());
        if (po == null) {
            throw new ParamException("没有找到要删除的文件！");
        }
        entCommonDaoService.deleteEntUploadInfo(param.getId());
        ossBizService.deleteContractFile(po.getFilePath());
        BaseMaResult result = new BaseMaResult();
        return result;
    }

    @Override
    public GetApplyInfoProvideResult getApplyInfoProvideInfo(GetApplyInfoProvideParam param) {
        getManager(param, Menu.M_110100);
        checkObject(param.getName());

        EntApplyInfoProvidePO po = entCommonDaoService.findProvideInfoByName(param.getName());
        if (po == null) {
            return new GetApplyInfoProvideResult();
        }
        GetApplyInfoProvideResult result = new GetApplyInfoProvideResult();
        result.setId(po.getId());
        result.setName(po.getName());
        result.setBusinessRegistrationNo(po.getBusinessRegistrationNo());
        result.setContactPhone(po.getContactPhone());
        result.setLegalRepresentative(po.getLegalRepresentative());
        result.setSocialCreditIdentifier(po.getSocialCreditIdentifier());
        return result;
    }

    public void validateParam(EditApplyInfoParam param) {
        checkString(param.getName());
        checkString(param.getRegistMoney());
        checkString(param.getAddress());
        checkString(param.getFoundDate());
        checkString(param.getLegalRepresentative());
        checkString(param.getLoanUsage());
        checkString(param.getLoanMoney());
        checkString(param.getSocialCreditIdentifier());
        checkPhone(param.getContactPhone());
        checkString(param.getBusinessRegistrationNo());
        checkObject(param.getLoanDate());
        checkObject(param.getLoanPeriod());
        checkObject(param.getBorrowStyle());
        checkString(param.getBankName());
        checkString(param.getBankAccountName());
        checkString(param.getBankNo());
        checkString(param.getContractNo());
    }

    public EntApplyPO convert(EditApplyInfoParam param) {
        EntApplyPO po = new EntApplyPO();

        po.setName(param.getName());
        po.setRegistMoney(param.getRegistMoney());
        po.setAddress(param.getAddress());
        po.setLoanMoney(param.getLoanMoney());
        try {
            po.setFoundDate(new SimpleDateFormat("yyyy-MM-dd").parse(param.getFoundDate()));
        } catch (ParseException e) {
            throw new ParamException("时间格式化异常！");
        }
        po.setLegalRepresentative(param.getLegalRepresentative());
        po.setLoanUsage(param.getLoanUsage());
        po.setCreateManagerId(param.getOperatorId());
        po.setApplyStatus(param.getApplyStatus());
        po.setContactPhone(param.getContactPhone());
        po.setSocialCreditIdentifier(param.getSocialCreditIdentifier());
        po.setBusinessRegistrationNo(param.getBusinessRegistrationNo());

        po.setLoanDate(param.getLoanDate());
        po.setLoanPeriod(param.getLoanPeriod());
        po.setBorrowStyle(param.getBorrowStyle());
        po.setBankName(param.getBankName());
        po.setBankAccountName(param.getBankAccountName());
        po.setBankNo(param.getBankNo());
        po.setContractNo(param.getContractNo());
        return po;
    }


}
