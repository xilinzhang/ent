package com.lianchuan.ma.entity.vo.result.system;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单信息
 */
@ApiModel(value = "MenuVO", description = "菜单信息")
public class MenuVO {
	@ApiModelProperty(value = "菜单编号", required = false)
	/** 菜单编号 */
	private Integer index;
	@ApiModelProperty(value = "菜单名称", required = false)
	/** 菜单名称 */
	private String text;
	@ApiModelProperty(value = "菜单标识", required = false)
	/** 菜单标识 */
	private String name;
	@ApiModelProperty(value = "是否是菜单项", required = false)
	/** 是否是菜单项 */
	private boolean leaf;
	@ApiModelProperty(value = "菜单图标", required = false)
	/** 菜单图标 */
	private String icon;
	@ApiModelProperty(value = "子菜单项", required = false)
	/** 子菜单项 */
	private List<MenuVO> items = new ArrayList<MenuVO>();

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<MenuVO> getItems() {
		return items;
	}

	public void setItems(List<MenuVO> items) {
		this.items = items;
	}

}
