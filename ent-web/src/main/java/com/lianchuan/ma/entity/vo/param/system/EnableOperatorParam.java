package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EnableOperatorParam", description = "启用或停用用户请求参数类")
public class EnableOperatorParam extends BaseOperatorParam {

	@ApiModelProperty(value = "目标用户ID", required = true)
	private Long id;

	/** @link com.lianchuan.common.entity.type.ma.EnableType */
	@ApiModelProperty(value = "是目标用户1启用  2停用", required = true)
	private Integer enableStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

}
