package com.lianchuan.common.entity.vo.app;

/**
 * 登录状态用户基础请求参数
 */
public class BaseUserParam extends BaseAppParam {

	/** 用户ID */
	private Long userId;

	/** 令牌 */
	private String token;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
