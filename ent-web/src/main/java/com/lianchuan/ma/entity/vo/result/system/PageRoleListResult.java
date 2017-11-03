package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.result.BaseMaPageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取角色列表
 */
@ApiModel(value = "PageRoleListResult", description = "获取角色列表")
public class PageRoleListResult extends BaseMaPageResult<RoleVO> {

	@ApiModelProperty(value = "角色信息", required = false)
	private List<RoleVO> roles = new ArrayList<RoleVO>();

	public List<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

}
