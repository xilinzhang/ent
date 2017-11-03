package com.lianchuan.ma.entity.vo.param.system;

import java.util.List;

import com.lianchuan.common.entity.type.ma.Dept;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改角色
 */
@ApiModel(value = "EditRoleParam", description = "修改角色")
public class EditRoleParam extends BaseOperatorParam {

	@ApiModelProperty(value = "角色ID", required = true)
	private Long roleId;

	@ApiModelProperty(value = "角色名称", required = true)
	private String name;

	/** 部门ID:{@link Dept} */
	@ApiModelProperty(value = "部门ID", required = true)
	private Integer deptId;

	/** 权限列表:{@link Menu} */
	@ApiModelProperty(value = "权限列表", required = true)
	private List<Integer> permissions;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public List<Integer> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Integer> permissions) {
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
