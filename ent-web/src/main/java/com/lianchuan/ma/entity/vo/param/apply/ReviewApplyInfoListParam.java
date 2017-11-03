package com.lianchuan.ma.entity.vo.param.apply;

import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ReviewApplyInfoListParam", description = "标的批量推送参数")
public class ReviewApplyInfoListParam extends BaseOperatorParam{
	@ApiModelProperty(value = "主键ID集合", required = false)
	List<Long> idList = new ArrayList<Long>();

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	
}
