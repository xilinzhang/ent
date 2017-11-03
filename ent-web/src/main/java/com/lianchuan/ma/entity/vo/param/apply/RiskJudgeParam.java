package com.lianchuan.ma.entity.vo.param.apply;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/*
 * 风控审批参数
 */
@ApiModel(value = "RiskJudgeParam", description = "风控审批参数")
public class RiskJudgeParam extends BaseOperatorParam {
	@ApiModelProperty(value = "标的id", required = false)
	private Long id;
	@ApiModelProperty(value = "标的状态，3 通过，4 不通过", required = false)
	private Integer applyStatus;
	@ApiModelProperty(value = "操作备注（状态为不通过时必填）", required = false)
	private String remark;

		public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	
}
