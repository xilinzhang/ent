package com.lianchuan.common.service.dao.ent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lianchuan.common.dao.ent.EntApplyDao;
import com.lianchuan.common.dao.ent.EntApplyInfoProvideDao;
import com.lianchuan.common.dao.ent.EntApplyOperatorLogDao;
import com.lianchuan.common.dao.ent.EntUploadInfoDao;
import com.lianchuan.common.dao.ma.MaManagerDao;
import com.lianchuan.common.entity.po.ent.EntApplyInfoProvidePO;
import com.lianchuan.common.entity.po.ent.EntApplyOperateLogPO;
import com.lianchuan.common.entity.po.ent.EntApplyPO;
import com.lianchuan.common.entity.po.ent.EntUploadInfoPO;
import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.vo.apply.ApplyParam;
import com.lianchuan.common.service.dao.AbstractDaoService;

/*
 * 标的通用service
 */
@Component
public class EntCommonDaoService extends AbstractDaoService {
	@Autowired
	private EntApplyDao entApplyDao;
	@Autowired
	private EntApplyOperatorLogDao entApplyOperatorLogDao;
	@Autowired
	private EntUploadInfoDao entUploadInfoDao;
	@Autowired
	private MaManagerDao maManagerDao;
	@Autowired
	private EntApplyInfoProvideDao entApplyInfoProvideDao;

	/**
	 * 条件查询企业工商信息申请列表
	 *
	 * @param applyParam
	 * @return
	 */
	public Page<EntApplyPO> findEntApply(ApplyParam applyParam, List<Integer> applyStatusList, String orderBy) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		sb.append("from ent_apply where 1=1");
		if (applyParam.getStartTime() != null) {
			sb.append(" and create_date >= ?");
			params.add(applyParam.getStartTime());
		}
		if (applyParam.getEndTime() != null) {
			sb.append(" and create_date < ?");
			params.add(applyParam.getEndTime());
		}
		if (applyParam.getCreateManagerId() != null) {
			sb.append(" and create_manager_id = ?");
			params.add(applyParam.getCreateManagerId());
		}

		sb.append(" and apply_status in ?");
		params.add(applyStatusList);

		return findPage(entApplyDao, sb.toString(), orderBy, params, applyParam.getPageNo(), applyParam.getPageSize());
	}
	/*
	 * 通过申请id查询企业工商申请信息
	 *
	 * @param applyId
	 *
	 * @return
	 */

	public EntApplyPO findEntApplyById(Long applyId) {
		return entApplyDao.findOne(applyId);
	}

	/*
	 * 编辑企业工商申请信息
	 * 
	 * @param po
	 */
	public EntApplyPO saveEntApplyInfo(EntApplyPO po) {
		return entApplyDao.save(po);
	}

	/*
	 * 查询最近一条数据
	 * 
	 * @param createDate
	 * 
	 * @return
	 */
	public EntApplyPO findMaxCodeByCreateDate(Date createDate) {
		String sql = "from ent_apply where create_date >=? ORDER BY create_date DESC,code DESC LIMIT 1";
		return findOne(entApplyDao, sql, new Object[] { createDate });
	}

	/*
	 *
	 */
	public List<EntApplyOperateLogPO> findEntApplyOperatorLogById(Long applyId) {
		return findList(entApplyOperatorLogDao, "from ent_apply_operate_log where apply_id = ?",
				new Object[] { applyId });
	}

	public List<EntUploadInfoPO> findEntUploadInfoByApplyId(Long applyId) {
		return findList(entUploadInfoDao, "from ent_upload_info where apply_id = ?", "order by create_date asc", new Object[] { applyId });
	}

	/*
	 *
	 * @param entApplyPO
	 *
	 * @param applyStatus
	 *
	 * @return
	 */
	public void updateApplyStatus(EntApplyPO entApplyPO, Integer applyStatus) {
		entApplyPO.setApplyStatus(applyStatus);
		entApplyDao.save(entApplyPO);

	}

	/*
	 *
	 * @param entApplyOperateLogPO
	 *
	 * @return
	 */
	public void saveEntApplyOperateLog(Long id, Integer applyStatus, String remark, Long operatorId) {

		EntApplyOperateLogPO entApplyOperateLogPO = new EntApplyOperateLogPO();
		entApplyOperateLogPO.setOperateType(applyStatus);
		entApplyOperateLogPO.setRemark(remark);
		entApplyOperateLogPO.setAuditManagerId(operatorId);

		entApplyOperateLogPO.setApplyId(id);
		entApplyOperateLogPO.setAuditDate(new Date());

		entApplyOperateLogPO.setCreateDate(new Date());
		entApplyOperatorLogDao.save(entApplyOperateLogPO);
	}

	/*
	 *
	 * @param id
	 *
	 * @return
	 */
	public MaManagerPO findManagerById(Long id) {
		return maManagerDao.findOne(id);
	}

	/**
	 * 获取文件
	 * 
	 * @param id
	 * @return
	 */
	public EntUploadInfoPO findEntUploadInfoById(Long id) {
		return entUploadInfoDao.findOne(id);
	}

	/**
	 * 保存合同文件
	 * 
	 * @param po
	 */
	public EntUploadInfoPO saveEntUploadInfo(EntUploadInfoPO po) {
		return entUploadInfoDao.save(po);
	}

	/**
	 * 根据id删除合同文件
	 * 
	 * @param id
	 */
	public void deleteEntUploadInfo(Long id) {
		entUploadInfoDao.delete(id);
	}

	public List<MaManagerPO> findAllManager() {
		return findList(maManagerDao, "from ma_manager where id <> ?", new Object[] { -1 });

	}

	/**
	 * 根据公司简称或者全称查询信息
	 * 
	 * @param name
	 * @return
	 */
	public EntApplyInfoProvidePO findProvideInfoByName(String name) {
		String sql = "from ent_apply_info_provide where name = ? ORDER BY create_date DESC LIMIT 1";
		return findOne(entApplyInfoProvideDao, sql, new Object[] { name });
	}

}
