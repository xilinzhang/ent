package com.lianchuan.ma.entity.vo.param.apply;

import com.lianchuan.ma.entity.vo.param.BaseMaPageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取标的申请列表
 **/
@ApiModel(value = "GetApplyInfoListParam", description = "搜索参数")
public class GetApplyInfoListParam extends BaseMaPageParam {

    @ApiModelProperty(value = "开始查询日期 格式:yyyy-MM-dd", required = false)
    private String startTime;

    @ApiModelProperty(value = "截止查询日期 格式:yyyy-MM-dd",required = false)
    private String endTime;

    @ApiModelProperty(value = "创建人ID",required = false)
    private Long createManagerId;

    @ApiModelProperty(value = "标的状态 1:新建 2:提交审核 3:审核通过 4:审核不通过 5:推送成功 6:推送失败",required = false)
    private Integer applyStatus;
    
    

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getCreateManagerId() {
        return createManagerId;
    }

    public void setCreateManagerId(Long createManagerId) {
        this.createManagerId = createManagerId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

}
