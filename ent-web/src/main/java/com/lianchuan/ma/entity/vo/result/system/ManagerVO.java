package com.lianchuan.ma.entity.vo.result.system;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员信息
 */

@ApiModel(value = "ManagerVO", description = "管理员信息")
public class ManagerVO {

	/** 账户唯一ID */
	@ApiModelProperty(value = "账户唯一ID", required = false)
	private Long id;
	/** 账户姓名 */
	@ApiModelProperty(value = "账户姓名", required = false)
	private String name;
	/** 账户账号 */
	@ApiModelProperty(value = "账户账号", required = false)
	private String username;
	/** 角色名称 */
	@ApiModelProperty(value = "角色名称", required = false)
	private String roleName;
	/** 部门名称 */
	@ApiModelProperty(value = "部门名称", required = false)
	private String deptName = "";
	/** 用户手机 */
	@ApiModelProperty(value = "用户手机", required = false)
	private String phone;
	/** 用户启用状态 {@link com.lianchuan.common.entity.type.ma.EnableType} */
	@ApiModelProperty(value = "用户启用状态 1启用  2停用", required = false)
	private Integer enableStatus = 1;
	/** 修改时间 */
	@ApiModelProperty(value = "修改时间", required = false)
	private Date modifyDate;
	/** 修改时间 */
	@ApiModelProperty(value = "创建时间", required = false)
	private Date createDate;
	/** 序号 */
	@ApiModelProperty(value = "序号", required = false)
	private Long no;
	/** 角色id */
	@ApiModelProperty(value = "角色id", required = false)
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
