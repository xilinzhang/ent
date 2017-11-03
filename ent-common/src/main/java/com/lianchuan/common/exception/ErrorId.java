package com.lianchuan.common.exception;

public interface ErrorId {

	String UNKNOWN = "服务器繁忙,请重试";

	/** 未知错误 */
	int ERROR_UNKNOWN = -1;
	/** 请求参数错误 */
	int ERROR_PARAM = -101;
	/** 令牌过期,请重新登录 */
	int ERROR_TOKEN = -103;
	/** 功能关闭 */
	int ERROR_ACTION_CLOSE = -106;

	/** 跳转至认证信息页面 */
	int ERROR_SYS_111 = -111;
	/** 认证错误 */
	int ERROR_SYS_115 = -115;
	/** 基础认证未填写 */
	int ERROR_SYS_201 = -201;
	
	
	// -----------info---------------
	
	/** 新用户首次登录修改密码 */
	int INFO_SYS_1 = 1;
	

}
