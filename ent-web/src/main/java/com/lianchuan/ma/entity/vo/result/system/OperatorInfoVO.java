package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员信息
 */
@ApiModel(value = "OperatorInfoVO", description = "管理员信息")
public class OperatorInfoVO {
	/** 管理员ID */
	@ApiModelProperty(value = "管理员id", required = false)
	private Long operatorId;
	@ApiModelProperty(value = "管理员名称", required = false)
	/** 管理员名称 */
	private String name;
	@ApiModelProperty(value = "手机", required = false)
	/** 手机 */
	private String phone;
	@ApiModelProperty(value = "管理员账号", required = false)
	/** 管理员账号 */
	private String username;
	@ApiModelProperty(value = "管理员角色id", required = false)
	/** 管理员角色ID */
	private Long roleId;
	@ApiModelProperty(value = "部门", required = false)
	/** 部门 */
	private Long deptId;
	@ApiModelProperty(value = "菜单列表", required = false)
	/** 菜单列表 */
	private List<MenuVO> menus = new ArrayList<MenuVO>();
	@ApiModelProperty(value = "特殊权限列表", required = false)
	/** 特殊权限列表 */
	private List<SpecialPermissionVO> specialPermissions = new ArrayList<SpecialPermissionVO>();

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<MenuVO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuVO> menus) {
		this.menus = menus;
	}

	public List<SpecialPermissionVO> getSpecialPermissions() {
		return specialPermissions;
	}

	public void setSpecialPermissions(List<SpecialPermissionVO> specialPermissions) {
		this.specialPermissions = specialPermissions;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
