package com.lianchuan.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础请求返回值
 */
@ApiModel(value = "BaseResult", description = "基础请求返回值")
public class BaseResult {

	@ApiModelProperty(value = "状态", required = false)
	private int status = 0;
	@ApiModelProperty(value = "描述", required = false)
	private String des;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
