package com.lianchuan.ma.entity.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页基础请求参数
 */
@ApiModel(value = "BaseMaPageParam", description = "翻页参数")
public class BaseMaPageParam extends BaseOperatorParam {

	@ApiModelProperty(value = "页数,从1开始", required = true)
	private int pageNo = 1;

	@ApiModelProperty(value = "一页几条", required = true)
	private int pageSize = 10;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
