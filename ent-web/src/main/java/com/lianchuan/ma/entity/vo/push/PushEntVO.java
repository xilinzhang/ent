package com.lianchuan.ma.entity.vo.push;

public class PushEntVO {

	private String sign;

	/**
	 * 盐
	 */
	private String salt;

	private PushDataVO data;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public PushDataVO getData() {
		return data;
	}

	public void setData(PushDataVO data) {
		this.data = data;
	}

}
