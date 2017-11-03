package com.lianchuan.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏数据工具类<br>
 * 姓名,手机号,身份证号,银行卡号等
 */
public class DesensitizationUtils {

	/**
	 * 获取脱敏后的手机号码
	 * 
	 * @param s
	 *            手机号码
	 * @return 136****1234
	 */
	public static String getPhone(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		StringBuilder phone = new StringBuilder();
		phone.append(s.substring(0, 3)).append("****").append(s.substring(s.length() - 4, s.length()));
		return phone.toString();
	}

	/**
	 * 获取脱敏后的身份证号码
	 * 
	 * @param s
	 *            身份证号码
	 * @return 4410**********0012
	 */
	public static String getIdentityCard(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		StringBuilder identityCard = new StringBuilder();
		identityCard.append(s.substring(0, 4)).append("**********").append(s.substring(s.length() - 4, s.length()));
		return identityCard.toString();
	}
	
	/**
	 * 获取脱敏后的字符串<br>
	 * 加密方式:加密后半段字符串
	 * 
	 * @param s
	 *            待加密字符串
	 * @return
	 */
	public static String getString(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		StringBuilder encrypt = new StringBuilder();
		int length = s.length() / 2;
		encrypt.append(s.substring(0, length));
		for (int i = length; i < s.length(); i++) {
			encrypt.append("*");
		}
		return encrypt.toString();
	}

}
