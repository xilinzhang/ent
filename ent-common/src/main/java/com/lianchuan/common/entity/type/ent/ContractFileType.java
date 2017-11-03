package com.lianchuan.common.entity.type.ent;

/**
 * 协议类型
 */
public enum ContractFileType {
	
	/** 销售合同 */
	CONTRACT_1(1, "销售合同"),
	/** 应收账款转让清单 */
	CONTRACT_2(2, "应收账款转让清单"),
	/** 应收账款转让通知书 */
	CONTRACT_3(3, "应收账款转让通知书"),
	/** 融资服务居间协议 */
	CONTRACT_4(4, "融资服务居间协议"),
	/** 法人身份证 */
	CONTRACT_5(5, "法人身份证"),
	/** 营业执照 */
	CONTRACT_6(6, "营业执照"),
	/** 开户许可证 */
	CONTRACT_7(7, "开户许可证"),
	/** 公司章程 */
	CONTRACT_8(8, "公司章程"),
	/** 借款合同 */
	CONTRACT_9(9, "借款合同"),
	/** 委托融资服务协议 */
	CONTRACT_10(10, "委托融资服务协议"),
	/** 担保函 */
	CONTRACT_11(11, "担保函"),
	/** 水泥购销合同 */
	CONTRACT_12(12, "水泥购销合同"),
	/** 借款人身份证 */
	CONTRACT_13(13, "借款人身份证"),
	/** 银行分期付款业务申请 */
	CONTRACT_14(14, "银行分期付款业务申请"),
	/** 借款协议 */
	CONTRACT_15(15, "借款协议"),
	/** 委托支付申请书 */
	CONTRACT_16(16, "委托支付申请书"),
	/** 委托合同 */
	CONTRACT_17(17, "委托合同");

	private int value;

	private String name;

	private ContractFileType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static ContractFileType getType(int value) {
		for (ContractFileType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}

}
