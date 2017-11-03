package com.lianchuan.ma.entity.vo.result.system;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取管理员信息
 */
@ApiModel(value = "GetOperatorInfoResult", description = "获取管理员信息")
public class GetOperatorInfoResult extends BaseMaResult {
	@ApiModelProperty(value = "管理员信息", required = false)
	/** 管理员信息 */
	private OperatorInfoVO operatorInfo;

	public OperatorInfoVO getOperatorInfo() {
		return operatorInfo;
	}

	public void setOperatorInfo(OperatorInfoVO operatorInfo) {
		this.operatorInfo = operatorInfo;
	}

}
