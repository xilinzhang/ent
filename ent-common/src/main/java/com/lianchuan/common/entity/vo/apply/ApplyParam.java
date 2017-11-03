package com.lianchuan.common.entity.vo.apply;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ApplyParam", description = "搜索参数")
public class ApplyParam {
	@ApiModelProperty(value = "页数,从1开始", required = true)
	private int pageNo = 1;

	@ApiModelProperty(value = "一页几条", required = true)
	private int pageSize = 10;
	
	@ApiModelProperty(value = "开始查询日期 格式:yyyy-MM-dd", required = false)
    private Date startTime;

    @ApiModelProperty(value = "截止查询日期 格式:yyyy-MM-dd",required = false)
    private Date endTime;

    @ApiModelProperty(value = "创建人ID",required = false)
    private Long createManagerId;

    @ApiModelProperty(value = "标的状态 1:新建 2:提交审核 3:审核通过 4:审核不通过 5:推送成功 6:推送失败",required = false)
    private Integer applyStatus;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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
