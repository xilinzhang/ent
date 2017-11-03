package com.lianchuan.common.entity.type.ent;

/**
 * 标的状态类型
 */
public enum ApplyStatus {

	/** 新建 */
	NEW_1(1, "新建"),
	/** 提交审核，待审核 */
	WAIT_AUDIT(2, "提交审核"),
	/** 审核通过,待推送 */
	AUDIT_PASS(3, "审核通过"),
	/** 审核不通过 */
	AUDIT_FAIL(4, "审核不通过"),
	/** 推送成功 */
	PUSH_SUCCESS(5, "推送成功"),
	/** 推送失败 */
	PUSH_FAIL(6, "推送失败");

	private int value;

	private String name;

	private ApplyStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static ApplyStatus getType(int value) {
		for (ApplyStatus type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
