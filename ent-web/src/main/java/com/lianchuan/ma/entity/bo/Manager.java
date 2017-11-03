package com.lianchuan.ma.entity.bo;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.common.entity.type.ma.Menu;

/**
 * 管理员信息
 */
public class Manager {

	/** 管理员唯一ID */
	private Long id;
	/** 令牌 */
	private String token;
	/** 令牌更新时间 */
	private long tokenTime;
	/** 角色ID */
	private Long roleId;

	List<Menu> permission = new ArrayList<Menu>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTokenTime(long tokenTime) {
		this.tokenTime = tokenTime;
	}

	public long getTokenTime() {
		return tokenTime;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Menu> getPermission() {
		return permission;
	}

	public void setPermission(List<Menu> permission) {
		this.permission = permission;
	}

}
