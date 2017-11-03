package com.lianchuan.ma.entity.vo.result.apply;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.UserVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/*
 * 获取所有的用户
 */
@ApiModel(value = "GetAllManagerResult", description = "获取所有的用户")
public class GetAllManagerResult extends BaseMaResult{
	@ApiModelProperty(value = "用户信息", required = false)
	List<UserVO> list = new ArrayList<UserVO>();

	public List<UserVO> getList() {
		return list;
	}

	public void setList(List<UserVO> list) {
		this.list = list;
	}
	
}
