package com.lianchuan.common.entity.type.ma.search;

/**
 * 管理员列表搜索类型
 */
public enum ManagerSearchType {

	/** 账号 */
	S_1(1, "账号"), //
	/** 名称 */
	S_2(2, "名称");//

	private int value;

	private String name;

	private ManagerSearchType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static ManagerSearchType getType(int value) {
		for (ManagerSearchType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
