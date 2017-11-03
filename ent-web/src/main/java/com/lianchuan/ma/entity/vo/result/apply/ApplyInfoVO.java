package com.lianchuan.ma.entity.vo.result.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 标的申请视图层信息
 */
@ApiModel(value = "ApplyInfoVO", description = "标的申请列表信息")
public class ApplyInfoVO {

    @ApiModelProperty(value = "唯一ID", required = false)
    private  Long  id;

    @ApiModelProperty(value = "创建时间", required = false)
    private String createDate;

    @ApiModelProperty(value = "创建人", required = false)
    private String createManagerName;

    @ApiModelProperty(value = "编号", required = false)
    private String code;

    @ApiModelProperty(value = "全称或简称", required = false)
    private String name;

    @ApiModelProperty(value = "注册资本（万元）", required = false)
    private String registMoney;

    @ApiModelProperty(value = "注册地址", required = false)
    private String address;

    @ApiModelProperty(value = "成立时间", required = false)
    private String foundDate;

    @ApiModelProperty(value = "法定代表人", required = false)
    private String LegalRepresentative;

    @ApiModelProperty(value = "借款用途", required = false)
    private String loanUsage;
    
    @ApiModelProperty(value = "借款金额", required = false)
    private String loanMoney;

    /** 标的状态 {@link com.lianchuan.common.entity.type.ent.ApplyStatus} */
    @ApiModelProperty(value = "标的状态 1:新建 2:提交审核 3:审核通过 4:审核不通过 5:推送成功 6:推送失败", required = false)
    private Integer applyStatus;

    @ApiModelProperty(value = "标的状态字段描述", required = false)
    private String applyStatusDesc;

    @ApiModelProperty(value = "合同附件信息", required = false)
    private List<ContractInfoVO> ContractInfoList=new ArrayList<ContractInfoVO>();

    @ApiModelProperty(value = "操作日志信息", required = false)
    private List<ApplyOperateLogVO> operateLogList=new ArrayList<ApplyOperateLogVO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateManagerName() {
        return createManagerName;
    }

    public void setCreateManagerName(String createManagerName) {
        this.createManagerName = createManagerName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistMoney() {
        return registMoney;
    }

    public void setRegistMoney(String registMoney) {
        this.registMoney = registMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getLegalRepresentative() {
        return LegalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        LegalRepresentative = legalRepresentative;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyStatusDesc() {
        return applyStatusDesc;
    }

    public void setApplyStatusDesc(String applyStatusDesc) {
        this.applyStatusDesc = applyStatusDesc;
    }

    public List<ContractInfoVO> getContractInfoList() {
        return ContractInfoList;
    }

    public void setContractInfoList(List<ContractInfoVO> contractInfoList) {
        ContractInfoList = contractInfoList;
    }

    public List<ApplyOperateLogVO> getOperateLogList() {
        return operateLogList;
    }

    public void setOperateLogList(List<ApplyOperateLogVO> operateLogList) {
        this.operateLogList = operateLogList;
    }
}
