package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseMaParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录请求参数
 */
@ApiModel(value = "LoginParam", description = "登录请求参数")
public class LoginParam extends BaseMaParam {
	@ApiModelProperty(value = "账号", required = false)
	/** 账号 */
	private String username;
	@ApiModelProperty(value = "密码", required = false)
	/** 密码 */
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
