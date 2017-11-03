package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改管理员角色
 */
@ApiModel(value = "EditOperatorParam", description = "修改管理员角色")
public class EditOperatorParam extends BaseOperatorParam {

	@ApiModelProperty(value = "角色ID", required = true)
	private Long roleId;

	@ApiModelProperty(value = "管理员ID ", required = true)
	private Long id;
	
	@ApiModelProperty(value = "用户姓名 ", required = true)
	private String name;

//	/** 账号 */
//	@ApiModelProperty(value = "用户账号 ", required = true)
//	private String username;
	
	/** 手机 */
	@ApiModelProperty(value = "手机 ", required = true)
	private String phone;
	

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

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

//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
