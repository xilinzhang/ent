package com.lianchuan.ma.entity.vo.result.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部门信息
 */
@ApiModel(value = "DeptVO", description = "部门信息")
public class DeptVO {

	@ApiModelProperty(value = "部门ID", required = false)
	private Integer id;

	@ApiModelProperty(value = "部门名称", required = false)
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
