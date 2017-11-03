package com.lianchuan.ma.entity.vo.push;

public class PushEntAccountVO {

	/**
	 * 账户类型 1企业
	 */
	private String accountType;
	/**
	 * 账户卡号
	 */
	private String bankCardNo;

	/**
	 * 开户行
	 */
	private String bankName;

	/**
	 * 公司名
	 */
	private String name;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
