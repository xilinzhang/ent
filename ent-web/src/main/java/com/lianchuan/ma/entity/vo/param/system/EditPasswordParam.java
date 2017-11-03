package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改密码
 */
@ApiModel(value = "EditPasswordParam", description = "修改密码")
public class EditPasswordParam extends BaseOperatorParam {
	@ApiModelProperty(value = "旧密码", required = false)
	/** 旧密码 */
	private String oldPassword;
	@ApiModelProperty(value = "新密码", required = false)
	/** 新密码 */
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
