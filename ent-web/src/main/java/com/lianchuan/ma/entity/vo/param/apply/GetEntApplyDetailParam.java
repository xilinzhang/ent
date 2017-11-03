package com.lianchuan.ma.entity.vo.param.apply;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/*
 * 风控审批详情
 */
@ApiModel(value = "GetEntApplyDetailParam", description = "搜索参数")
public class GetEntApplyDetailParam extends BaseOperatorParam {
	@ApiModelProperty(value = "标的id", required = false)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
