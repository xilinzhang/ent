package com.lianchuan.common.entity.type.ma;

/**
 * 启用停用状态
 */
public enum EnableType {

	/** 启用 */
	ENABLE_1(1, "启用"),
	/** 停用 */
	DISABLE_2(2, "停用");

	private int value;

	private String name;

	private EnableType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static EnableType getType(int value) {
		for (EnableType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
