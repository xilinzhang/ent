package com.lianchuan.common.entity.sql;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.common.entity.po.BasePO;

/**
 * 数据层分页信息
 * 
 * @param <T>
 */
public class Page<T extends BasePO> {

	private int pageNo = 1;

	private int pageMax = 0;
	/** 数据总数,不是info的数量 */
	private long count = 0;

	private List<T> info = new ArrayList<T>();

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

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

	public List<T> getInfo() {
		return info;
	}

	public void setInfo(List<T> info) {
		this.info = info;
	}

	public void add(T t) {
		info.add(t);
	}

}
