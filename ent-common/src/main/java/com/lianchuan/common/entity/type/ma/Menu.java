package com.lianchuan.common.entity.type.ma;

/**
 * 菜单(菜单权限)
 */
public enum Menu {
	// value, text, name, 是否叶子节点, 父节点(菜单), 图标, 是否菜单
	
	// 系统管理
	/** 系统管理 */
	M_100000(100000, "系统管理", "system", false, null, "setting", true), //
	/** 角色管理 */
	M_100200(100200, "角色管理", "roleList", true, M_100000, "setting", true), //
	/** 用户管理 */
	M_100300(100300, "用户管理", "operatorList", true, M_100000, "setting", true), //
	
	/** 个人中心 */
	M_101000(101000, "个人中心", "system", false, null, "setting", true), //
	/** 修改密码 */
	M_101100(101100, "修改密码", "editPassword", true, M_101000, "setting", true), //
	/** 首页 */
	M_102000(102000, "首页", "indexPage", false, null, "setting", true), //

	/** 业务管理 */
	M_110000(110000, "业务发起", "businessApply", false, null, "business", true), //
	/** 业务列表 */
	M_110100(110100, "业务列表", "applyList", true, M_110000, "business", true), //
	
	/** 风控管理 */
	M_120000(120000, "风控管理", "businessAudit", false, null, "audit", true), //
	/** 风控审批 */
	M_120100(120100, "风控审批", "auditList", true, M_120000, "audit", true), //
	
	/** 标的推送管理 */
	M_130000(130000, "标的推送管理", "businessPush", false, null, "push", true), //
	/** 标的推送列表 */
	M_130100(130100, "标的推送列表", "pushList", true, M_130000, "push", true), //
	
	// 特殊权限
	/** 特殊权限 */
	P_1000000(1000000, "特殊权限", "permission", false, null, null, false), //
	;

	private int value;

	private String text;

	private String name;
	/** 是否叶子节点 */
	private boolean leaf;
	/** 父节点(菜单) */
	private Menu parent;
	/** 图标(http://ant.design/) */
	private String icon;
	/** 是否菜单 */
	private boolean menu;

	private Menu(int value, String text, String name, boolean leaf, Menu parent, String icon, boolean menu) {
		this.value = value;
		this.text = text;
		this.name = name;
		this.leaf = leaf;
		this.parent = parent;
		this.icon = icon;
		this.menu = menu;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public String getName() {
		return name;
	}

	public Menu getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public String getIcon() {
		return icon;
	}

	public boolean isMenu() {
		return menu;
	}

	public static Menu getMenu(int value) {
		for (Menu type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
