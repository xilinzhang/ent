package com.lianchuan.common.entity.type.ent;

/**
 * 证件类型
 *
 */
public enum IdentityType {

	/** 居民身份证 */
	RESIDENT_IDENTITY_1(1, "居民身份证"),
	/** 香港特区护照/身份证明 */
	HONGKONG_PASSPORT_2(2, "香港特区护照/身份证明"),
	/** 澳门特区护照/身份证明 */
	MACAO_PASSPORT_3(3, "澳门特区护照/身份证明"),
	/** 澳门特区护照/身份证明 */
	TAIWAN_PASSPORT_4(4, "台湾居民来往大陆通行证"),
	/** 澳门特区护照/身份证明 */
	PASSPORT_5(5, "护照");
	
	
	private int value;

	private String name;

	private IdentityType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static IdentityType getType(int value) {
		for (IdentityType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}