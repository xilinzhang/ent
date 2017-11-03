package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseMaPageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取角色列表
 */
@ApiModel(value = "GetRoleListParam", description = "搜索参数")
public class GetRoleListParam extends BaseMaPageParam {

	/** 查询值 */
	@ApiModelProperty(value = "查询值", required = false)
	private String searchValue;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
