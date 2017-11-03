package com.lianchuan.ma.entity.vo.result.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同信息
 **/

@ApiModel(value = "ContractInfoVO", description = "合同信息")
public class ContractInfoVO {

    @ApiModelProperty(value = "合同文件ID", required = false)
    private Long fileId;

    @ApiModelProperty(value = "合同文件名", required = false)
    private String contractName;
    
    @ApiModelProperty(value = "合同文件类型 1:销售合同 2:应收账款转让清单 3:应收账款转让通知书 4:融资服务居间协议 " +
			"5:法人身份证 6:营业执照 7:开户许可证 8:公司章程 9:借款合同 10:委托融资服务协议 11:担保函" +
			"12:水泥购销合同 13:借款人身份证 14:银行分期付款业务申请 15:借款协议 16:委托支付申请书 17:委托合同", required = false)
    private Integer contractFileType;

	public Integer getContractFileType() {
		return contractFileType;
	}

	public void setContractFileType(Integer contractFileType) {
		this.contractFileType = contractFileType;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
}
