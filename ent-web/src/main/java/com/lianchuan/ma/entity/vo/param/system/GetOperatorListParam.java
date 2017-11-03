package com.lianchuan.ma.entity.vo.param.system;

import com.lianchuan.ma.entity.vo.param.BaseMaPageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取管理员列表
 */
@ApiModel(value = "GetOperatorListParam", description = "搜索参数")
public class GetOperatorListParam extends BaseMaPageParam {

	/** 查询类型,{@link ManagerSearchType} */
	@ApiModelProperty(value = "查询类型1账号 2名称", required = false)
	private Integer searchType;

	/** 查询值 */
	@ApiModelProperty(value = "查询值", required = false)
	private String searchValue;

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
