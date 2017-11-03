package com.lianchuan.ma.entity.vo.result.apply;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zxl on 2017-10-24
 **/
@ApiModel(value = "EditContractInfoResult", description = "编辑合同文件")
public class EditContractInfoResult extends BaseMaResult {
    @ApiModelProperty(value = "合同ID", required = false)
    private Long contractId;
    @ApiModelProperty(value = "文件名称", required = false)
    private String fileName;
    @ApiModelProperty(value = "合同文件类型  1:融资服务居间协议 2:销售合同 3:借款合同 4:担保合同 5:三方框架协议", required = false)
    private Integer contractFileType;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getContractFileType() {
        return contractFileType;
    }

    public void setContractFileType(Integer contractFileType) {
        this.contractFileType = contractFileType;
    }
}
