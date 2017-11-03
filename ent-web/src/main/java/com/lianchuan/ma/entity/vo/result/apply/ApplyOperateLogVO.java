package com.lianchuan.ma.entity.vo.result.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * Created by zxl on 2017-10-23
 **/
@ApiModel(value = "ApplyOperateLogVO", description = "视图层操作日志信息")
public class ApplyOperateLogVO {

    @ApiModelProperty(value = "标的ID", required = false)
    private Long applyId;

    @ApiModelProperty(value = "建人，审核人，推送人", required = false)
    private String auditName;

    @ApiModelProperty(value = "审核时间", required = false)
    private String auditDate;

    /** 标的操作状态 {@link com.lianchuan.common.entity.type.ent.OperateType} */
    @ApiModelProperty(value = "标的操作状态 1:新建 2:提交审核 3:审核通过 4:审核不通过 5:推送成功 6:推送失败", required = false)
    private Integer operateType;

    @ApiModelProperty(value = "操作备注", required = false)
    private String remark;

    @ApiModelProperty(value = "标的操作状态 1:新建 2:提交审核 3:审核通过 4:审核不通过 5:推送成功 6:推送失败", required = false)
    private String operateTypeName;
    
    public String getOperateTypeName() {
		return operateTypeName;
	}

	public void setOperateTypeName(String operateTypeName) {
		this.operateTypeName = operateTypeName;
	}

	public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
