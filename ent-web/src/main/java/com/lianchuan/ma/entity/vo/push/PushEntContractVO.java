package com.lianchuan.ma.entity.vo.push;

public class PushEntContractVO {
	/**
	 * 借款详情
	 */
	private String borrowContent;

	/**
	 * 还款方式
	 */
	private Integer borrowStyle;
	/**
	 * 借款用途
	 */
	private String borrowUse;
	/**
	 * 合同利率0
	 */
	private float contractInterest;
	/**
	 * 合同协议号
	 */
	private String contractNo;
	/**
	 * 借款有效结束时间 stamp
	 */
	private Long endTime;

	/**
	 * 借款有效开始时间stamp
	 */
	private Long startTime;

	public String getBorrowContent() {
		return borrowContent;
	}

	public void setBorrowContent(String borrowContent) {
		this.borrowContent = borrowContent;
	}

	public Integer getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(Integer borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowUse() {
		return borrowUse;
	}

	public void setBorrowUse(String borrowUse) {
		this.borrowUse = borrowUse;
	}

	public float getContractInterest() {
		return contractInterest;
	}

	public void setContractInterest(float contractInterest) {
		this.contractInterest = contractInterest;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

}
