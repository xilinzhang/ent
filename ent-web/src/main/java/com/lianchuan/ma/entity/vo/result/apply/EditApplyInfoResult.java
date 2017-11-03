package com.lianchuan.ma.entity.vo.result.apply;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zxl on 2017-10-27
 **/
@ApiModel(value = "EditApplyInfoResult", description = "新增或者编辑标的信息")
public class EditApplyInfoResult extends BaseMaResult {

    @ApiModelProperty(value = "标的ID", required = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
