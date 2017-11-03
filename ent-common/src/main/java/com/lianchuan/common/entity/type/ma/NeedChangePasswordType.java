package com.lianchuan.common.entity.type.ma;

/**
 * 启用停用状态
 */
public enum NeedChangePasswordType {

	/** 不需要 */
	NO_NEED_0(0, "不需要"),
	/** 需要 */
	NEED_1(1, "需要");

	private int value;

	private String name;

	private NeedChangePasswordType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static NeedChangePasswordType getType(int value) {
		for (NeedChangePasswordType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
