package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取权限列表
 */
@ApiModel(value = "GetPermissionListResult", description = "获取权限列表")
public class GetPermissionListResult extends BaseMaResult {

	@ApiModelProperty(value = "权限列表", required = false)
	private List<PermissionVO> infos = new ArrayList<PermissionVO>();

	public List<PermissionVO> getInfos() {
		return infos;
	}

	public void setInfos(List<PermissionVO> infos) {
		this.infos = infos;
	}
}
