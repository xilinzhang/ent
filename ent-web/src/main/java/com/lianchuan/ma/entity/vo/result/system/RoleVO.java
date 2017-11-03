package com.lianchuan.ma.entity.vo.result.system;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色信息
 */
@ApiModel(value = "RoleVO", description = "角色信息")
public class RoleVO {

	@ApiModelProperty(value = "角色ID", required = false)
	private Long id;
	@ApiModelProperty(value = "角色名称 ", required = false)
	private String name;
	@ApiModelProperty(value = "部门名称", required = false)
	private String deptName = "";
	@ApiModelProperty(value = "最后操作人", required = false)
	private String lastEditUser = "";
	@ApiModelProperty(value = "创建时间", required = false)
	private Date createDate;
	@ApiModelProperty(value = "最后修改时间", required = false)
	private Date modifyDate;
	
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLastEditUser() {
		return lastEditUser;
	}

	public void setLastEditUser(String lastEditUser) {
		this.lastEditUser = lastEditUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
