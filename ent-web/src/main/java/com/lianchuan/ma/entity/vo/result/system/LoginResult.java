package com.lianchuan.ma.entity.vo.result.system;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录
 */
@ApiModel(value = "LoginResult", description = "登陆，status=0正常登录，status=1首次登录修改密码")
public class LoginResult extends BaseMaResult {

	/** 用户唯一ID */
	@ApiModelProperty(value = "账户唯一ID", required = false)
	private Long operatorId;
	/** 登录令牌 */
	@ApiModelProperty(value = "登录令牌", required = false)
	private String token;
	/** 登录用户信息 */
	@ApiModelProperty(value = "登录用户信息", required = false)
	private OperatorInfoVO operatorInfo;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public OperatorInfoVO getOperatorInfo() {
		return operatorInfo;
	}

	public void setOperatorInfo(OperatorInfoVO operatorInfo) {
		this.operatorInfo = operatorInfo;
	}

}
