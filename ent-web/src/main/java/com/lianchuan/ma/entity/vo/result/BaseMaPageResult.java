package com.lianchuan.ma.entity.vo.result;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.param.BaseMaPageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页基础返回参数
 * 
 * @param <T>
 */

@ApiModel(value = "BaseMaPageResult", description = "分页基础返回参数")
public class BaseMaPageResult<T> extends BaseMaResult {

	@ApiModelProperty(value = "页数", required = false)
	private int pageNo = 1;
	@ApiModelProperty(value = "最大页数", required = false)
	private int pageMax;
	@ApiModelProperty(value = "数据总数", required = false)
	/** 数据总数 */
	private long totalNum;

	private List<T> infos = new ArrayList<T>();

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageMax() {
		return pageMax;
	}

	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	public List<T> getInfos() {
		return infos;
	}

	public void setInfos(List<T> infos) {
		this.infos = infos;
	}

}
