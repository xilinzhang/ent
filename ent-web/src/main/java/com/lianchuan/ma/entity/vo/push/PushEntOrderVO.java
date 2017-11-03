package com.lianchuan.ma.entity.vo.push;

public class PushEntOrderVO {

	/**
	 * 订单号，本系统内唯一
	 */
	private String orderNo;

	/**
	 * 期限 7天/14天
	 */
	private String orderPeriod;

	/**
	 * 订单生成时间timestamp毫秒
	 */
	private Long orderTime;

	/**
	 * 总额,单位分
	 */
	private Long orderTotal;

	/**
	 * 分标 默认1
	 */
	private Integer fz;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(String orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	public Long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}

	public Long getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Long orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Integer getFz() {
		return fz;
	}

	public void setFz(Integer fz) {
		this.fz = fz;
	}

}
