package com.lianchuan.common.entity.type.ma;

/**
 * 部门
 */
public enum Dept {

	/** 客服部 */
	D_1(1, "客服部"), //
	/** 运营部 */
	D_2(2, "运营部"), //
	/** 开发部 */
	D_3(3, "开发部"), //
	/** 商务部 */
	D_4(4, "商务部"), //
	/** 财务部 */
	D_5(5, "财务部"), //
	/** 风控部 */
	D_6(6, "风控部");//

	private int value;

	private String name;

	private Dept(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static Dept getType(int value) {
		for (Dept type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
