package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AddOperatorParam", description = "添加管理员请求参数类")
public class AddOperatorParam extends BaseOperatorParam {

	@ApiModelProperty(value = "姓名", required = true)
	private String name;

	@ApiModelProperty(value = "帐号", required = true)
	private String username;

	@ApiModelProperty(value = "角色ID", required = true)
	private Long roleId;
	
	@ApiModelProperty(value = "手机", required = true)
	private String phone;

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

}
