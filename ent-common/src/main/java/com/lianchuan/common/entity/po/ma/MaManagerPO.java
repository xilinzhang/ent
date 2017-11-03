package com.lianchuan.common.entity.po.ma;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;

/**
 * 后台管理员
 */
@Entity
@Table(name = DaoGlobal.MA_TABLE_BASE_NAME + "manager")
public class MaManagerPO extends BasePO {

	private static final long serialVersionUID = 5088417456837218949L;

	/** 管理员名称,可以重复 */
	@Column(nullable = false, unique = false, length = 255)
	private String name;

	/** 管理员账号 */
	@Column(nullable = false, unique = true, length = 255)
	private String username;
	
	/** 管理员手机 */
	@Column(nullable = false, unique = false, length = 255)
	private String phone;

	/** 管理员密码 */
	@Column(nullable = false, length = 255)
	private String password;
	
	/** 用户启用状态 {@link com.lianchuan.common.entity.type.ma.EnableType} */
	@Column(nullable = false)
	private Integer enableStatus = 1;

	/** 角色ID:{@link MaManagerRolePO} */
	@Column(nullable = false)
	private Long roleId;
	
	/** 修改时间 */
	@Column(nullable = false)
	private Date modifyDate = new Date();

	/** 是否需要修改密码（首次登录）{@link com.lianchuan.common.entity.type.ma.NeedChangePasswordType} */
	@Column(nullable = false)
	private Integer needChangePwd = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getNeedChangePwd() {
		return needChangePwd;
	}

	public void setNeedChangePwd(Integer needChangePwd) {
		this.needChangePwd = needChangePwd;
	}

}
