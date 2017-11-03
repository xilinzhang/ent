package com.lianchuan.ma.entity.vo.result.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 权限信息
 */
@ApiModel(value = "PermissionVO", description = "权限信息")
public class PermissionVO {

	@ApiModelProperty(value = "权限ID", required = false)
	private Integer id;

	@ApiModelProperty(value = "权限名称", required = false)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
