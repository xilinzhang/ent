package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取菜单列表
 */
@ApiModel(value = "GetMenuListResult", description = "获取菜单列表")
public class GetMenuListResult extends BaseMaResult {
	@ApiModelProperty(value = "菜单列表", required = false)
	/** 菜单列表 */
	private List<MenuVO> menus = new ArrayList<MenuVO>();

	public List<MenuVO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuVO> menus) {
		this.menus = menus;
	}

}
