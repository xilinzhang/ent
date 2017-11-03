package com.lianchuan.ma.entity.vo.param;

import com.lianchuan.common.entity.vo.BaseParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员操作的基础请求参数类
 */
@ApiModel(value = "BaseOperatorParam", description = "管理员操作的基础请求参数类")
public class BaseOperatorParam extends BaseParam {

	@ApiModelProperty(value = "管理员ID", required = true)
	private Long operatorId;

	@ApiModelProperty(value = "令牌", required = true)
	private String token;

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

}
