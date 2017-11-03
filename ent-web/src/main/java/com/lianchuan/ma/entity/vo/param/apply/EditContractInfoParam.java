package com.lianchuan.ma.entity.vo.param.apply;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zxl on 2017-10-23
 **/
@ApiModel(value = "EditContractInfoParam", description = "编辑合同参数")
public class EditContractInfoParam extends BaseOperatorParam {

    @ApiModelProperty(value = "合同ID", required = false)
    private Long contractId;

//    @ApiModelProperty(value = "合同文件类型  1:销售合同 2:应收账款转让清单 3:应收账款转让通知书 4:融资服务居间协议" +
//            "5:法人身份证 6:营业执照 7:开户许可证 8:公司章程 9:借款合同 10:委托融资服务协议 11:担保函" +
//            "12:水泥购销合同 13:借款人身份证 14:银行分期付款业务申请 15:借款协议 16:委托支付申请书 17:委托合同", required = false)
//    private  int ContractFileType;

    @ApiModelProperty(value = "合同文件", required = false)
    private MultipartFile multipartFile;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

//    public int getContractFileType() {
//        return ContractFileType;
//    }
//
//    public void setContractFileType(int contractFileType) {
//        ContractFileType = contractFileType;
//    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
