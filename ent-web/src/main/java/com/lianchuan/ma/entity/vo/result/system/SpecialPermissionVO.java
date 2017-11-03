package com.lianchuan.ma.entity.vo.result.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 特殊权限信息
 */
@ApiModel(value = "SpecialPermissionVO", description = "特殊权限信息")
public class SpecialPermissionVO {
	@ApiModelProperty(value = "特殊权限编号", required = false)
	/** 特殊权限编号 */
	private Integer index;
	@ApiModelProperty(value = "特殊权限名称", required = false)
	/** 特殊权限名称 */
	private String text;
	@ApiModelProperty(value = "特殊权限标识", required = false)
	/** 特殊权限标识 */
	private String name;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
