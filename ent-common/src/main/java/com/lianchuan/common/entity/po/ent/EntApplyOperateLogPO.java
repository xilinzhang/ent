package com.lianchuan.common.entity.po.ent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;

/***
 * 企业工商信息申请操作记录
 */
@Entity
@Table(name = DaoGlobal.ENT_TABLE_BASE_NAME + "apply_operate_log")
public class EntApplyOperateLogPO extends BasePO {

	private static final long serialVersionUID = -7942124101958390252L;

	/** 标的ID */
	@Column(nullable = false)
	private Long applyId;

	/** 创建人，审核人，推送人 */
	@Column(nullable = false)
	private Long auditManagerId;

	/** 审核时间 */
	@Column(nullable = false)
	private Date auditDate;

	/** 标的操作状态 {@link com.lianchuan.common.entity.type.ent.OperateType} */
	@Column(nullable = false)
	private Integer operateType;

	/** 操作备注 */
	@Column(nullable = true, length = 255)
	private String remark;

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getAuditManagerId() {
		return auditManagerId;
	}

	public void setAuditManagerId(Long auditManagerId) {
		this.auditManagerId = auditManagerId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
