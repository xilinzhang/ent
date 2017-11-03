package com.lianchuan.common.entity.type;

/**
 * 查询条件匹配类型
 */
public enum MappingType {

	/** = ? */
	EQUAL(1, "等于"), //
	/** like ?% */
	HALF(2, "半模糊"), //
	/** like %?% */
	FULL(3, "全模糊");

	private int value;

	private String name;

	private MappingType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static MappingType getType(int value) {
		for (MappingType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
