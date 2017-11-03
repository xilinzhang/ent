package com.lianchuan.common.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5Utils {

	/** 全局数组 */
	private final static String[] STRING_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/** MD5算法名称 */
	private final static String ALGORITHM = "MD5";

	/** 字符串转码格式 */
	private final static Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * 使用MD5算法加密字符串
	 * 
	 * @param s
	 * @return 加密后的字符串(小写)
	 */
	public static String encrypt(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] b = s.getBytes(CHARSET);
			String resultString = byteToString(md.digest(b));
			return resultString;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 使用MD5算法加密字符串
	 * 
	 * @param s
	 * @return 加密后的字符串(大写)
	 */
	public static String encryptToUpperCase(String s) {
		return encrypt(s).toUpperCase();
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param bByte
	 * @return
	 */
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	/**
	 * 转换字节类型为16进制字串
	 * 
	 * @param bByte
	 * @return
	 */
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int id1 = iRet / 16;
		int id2 = iRet % 16;
		return STRING_DIGITS[id1] + STRING_DIGITS[id2];
	}

}
