package com.lianchuan.ma.entity.vo.param.apply;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zxl on 2017-10-23
 **/
@ApiModel(value = "ReviewApplyInfoParam", description = "提交审核参数")
public class ReviewApplyInfoParam extends BaseOperatorParam {

    @ApiModelProperty(value = "主键ID", required = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
