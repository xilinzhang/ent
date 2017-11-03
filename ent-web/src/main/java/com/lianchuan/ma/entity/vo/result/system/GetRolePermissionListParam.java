package com.lianchuan.ma.entity.vo.result.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取角色权限列表
 */
@ApiModel(value = "GetRolePermissionListParam", description = "获取角色权限列表")
public class GetRolePermissionListParam extends BaseOperatorParam {

	@ApiModelProperty(value = "角色ID", required = true)
	private Long roleId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
