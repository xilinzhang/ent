package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ResetPasswordParam", description = "重置管理员密码请求参数类")
public class ResetPasswordParam extends BaseOperatorParam {

	@ApiModelProperty(value = "重置密码用户ID", required = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
