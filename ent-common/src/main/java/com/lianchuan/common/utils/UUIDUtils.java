package com.lianchuan.common.utils;

import java.util.UUID;

/**
 * 唯一ID工具类
 */
public class UUIDUtils {

	/**
	 * 获取32位唯一字符串
	 * 
	 * @return
	 */
	public static String uuid() {
		return MD5Utils.encrypt(UUID.randomUUID().toString());
	}

}
