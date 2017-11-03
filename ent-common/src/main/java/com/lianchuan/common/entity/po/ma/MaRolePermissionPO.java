package com.lianchuan.common.entity.po.ma;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;
import com.lianchuan.common.entity.type.ma.Menu;

/**
 * 角色对应权限
 */
@Entity
@Table(name = DaoGlobal.MA_TABLE_BASE_NAME + "role_permission")
public class MaRolePermissionPO extends BasePO {

	private static final long serialVersionUID = 5417393780007928200L;

	/** 角色ID:{@link MaManagerRolePO} */
	@Column(nullable = false)
	private Long roleId;

	/** 权限ID:{@link Menu} */
	@Column(nullable = false)
	private Integer permissionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

}
