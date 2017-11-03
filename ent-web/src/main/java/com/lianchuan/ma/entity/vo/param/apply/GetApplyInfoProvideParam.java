package com.lianchuan.ma.entity.vo.param.apply;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zxl on 2017-10-31
 * 老数据提供查询参数
 **/
@ApiModel(value = "GetApplyInfoProvideParam", description = "搜索参数")
public class GetApplyInfoProvideParam extends BaseOperatorParam {

    @ApiModelProperty(value = "公司全称或者简称", required = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
