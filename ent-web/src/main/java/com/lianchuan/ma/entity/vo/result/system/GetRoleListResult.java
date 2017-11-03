package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取所有角色列表
 */
@ApiModel(value = "GetRoleListResult", description = "获取所有角色列表")
public class GetRoleListResult extends BaseMaResult {

	@ApiModelProperty(value = "角色列表", required = false)
	private List<RoleVO> roles = new ArrayList<RoleVO>();

	public List<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

}
