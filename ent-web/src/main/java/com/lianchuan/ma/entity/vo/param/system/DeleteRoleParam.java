package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 删除角色
 */
@ApiModel(value = "DeleteRoleParam", description = "删除角色")
public class DeleteRoleParam extends BaseOperatorParam {

	/** 角色id */
	@ApiModelProperty(value = "角色id", required = true)
	private Long roleId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	
}
