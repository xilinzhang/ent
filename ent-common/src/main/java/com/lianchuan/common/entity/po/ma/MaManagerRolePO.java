package com.lianchuan.common.entity.po.ma;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;
import com.lianchuan.common.entity.type.ma.Dept;

/***
 * 管理员角色
 */
@Entity
@Table(name = DaoGlobal.MA_TABLE_BASE_NAME + "manager_role")
public class MaManagerRolePO extends BasePO {

	private static final long serialVersionUID = -3730447524723407528L;

	/** 角色名称 */
	@Column(nullable = false, unique = true, length = 255)
	private String name;

	/** 部门:{@link Dept} */
	@Column
	private Integer dept;
	
	/** 最新更新用户id */
	@Column
	private Long lastEditOperatorId;

	/** 创建时间 */
	@Column(nullable = false)
	private Date modifyDate = new Date();

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLastEditOperatorId() {
		return lastEditOperatorId;
	}

	public void setLastEditOperatorId(Long lastEditOperatorId) {
		this.lastEditOperatorId = lastEditOperatorId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
