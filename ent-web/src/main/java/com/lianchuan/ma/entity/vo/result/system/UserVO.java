package com.lianchuan.ma.entity.vo.result.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/*
 * 用户信息
 */
@ApiModel(value = "UserVO", description = "用户信息")
public class UserVO {
	/** 账户唯一ID */
	@ApiModelProperty(value = "账户唯一ID", required = false)
	private Long id;
	/** 账户姓名 */
	@ApiModelProperty(value = "账户姓名", required = false)
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
