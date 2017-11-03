package com.lianchuan.common.entity.type.ent;

/**
 * 标的提交历史及审核记录状态
 */
public enum OperateType {

	/** 新建 */
	NEW_1(1, "新建"),
	/** 提交审核 */
	WAIT_AUDIT(2, "提交审核"),
	/** 审核通过 */
	AUDIT_PASS(3, "审核通过"),
	/** 审核 */
	AUDIT_FAIL(4, "审核不通过"),
	/** 推送成功 */
	PUSH_SUCCESS(5, "推送成功"),
	/** 推送失败 */
	PUSH_FAIL(6, "推送失败");

	private int value;

	private String name;

	private OperateType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static OperateType getType(int value) {
		for (OperateType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
