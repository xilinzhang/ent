package com.lianchuan.ma.utils;

import com.lianchuan.common.utils.MD5Utils;

/**
 * 密码工具类
 */
public class PasswordUtils {

	/**
	 * 加密
	 * 
	 * @param plaintext
	 *            明文密码
	 * @param username
	 *            账号
	 * @return
	 */
	public static String encrypt(String plaintext, String username) {
		return MD5Utils.encrypt(plaintext + username);
	}

}
