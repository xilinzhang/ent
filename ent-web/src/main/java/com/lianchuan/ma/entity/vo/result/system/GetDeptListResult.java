package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取部门信息
 */
@ApiModel(value = "GetDeptListResult", description = "获取部门信息")
public class GetDeptListResult extends BaseMaResult {

	@ApiModelProperty(value = "部门列表", required = false)
	private List<DeptVO> infos = new ArrayList<DeptVO>();

	public List<DeptVO> getInfos() {
		return infos;
	}

	public void setInfos(List<DeptVO> infos) {
		this.infos = infos;
	}
}
