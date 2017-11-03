package com.lianchuan.common.entity.type.ent;

public enum BorrowStyleType {

	/** 等额本金 */
	BORROWSTYLE_0(0, "等额本金"),
	/** 等额本息 */
	BORROWSTYLE_1(1, "等额本息"),
	/** 一次还本付息 */
	BORROWSTYLE_2(2, "一次还本付息"),
	/** 按月付息，到期还本 */
	BORROWSTYLE_3(3, "按月付息，到期还本")
	;

	private int value;

	private String name;

	private BorrowStyleType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static BorrowStyleType getType(int value) {
		for (BorrowStyleType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}