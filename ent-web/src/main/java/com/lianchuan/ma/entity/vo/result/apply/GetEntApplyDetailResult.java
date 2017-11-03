package com.lianchuan.ma.entity.vo.result.apply;

import java.util.*;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * 审批详情
 */
@ApiModel(value = "GetEntApplyDetailResult", description = "审批详情")
public class GetEntApplyDetailResult extends BaseMaResult {

	@ApiModelProperty(value = "标的id", required = false)
	private Long id;
	@ApiModelProperty(value = "全称或简称", required = false)
	private String name;
	@ApiModelProperty(value = "注册资本", required = false)
	private String registMoney;
	@ApiModelProperty(value = "注册地址", required = false)
	private String address;
	@ApiModelProperty(value = "成立时间", required = false)
	private Date foundDate;

	@ApiModelProperty(value = "联系方式", required = false)
	private String contactPhone;
	@ApiModelProperty(value = "统一社会信用代码", required = false)
	private String socialCreditIdentifier;
	@ApiModelProperty(value = "营业执照代码", required = false)
	private String businessRegistrationNo;

	@ApiModelProperty(value = "法定代表人", required = false)
	private String LegalRepresentative;
	@ApiModelProperty(value = "借款用途", required = false)
	private String loanUsage;
	@ApiModelProperty(value = "借款金额", required = false)
	private String loanMoney;

	@ApiModelProperty(value = "借款日期", required = false)
	private Date loanDate;
	@ApiModelProperty(value = "借款期限", required = false)
	private Integer loanPeriod;
	@ApiModelProperty(value = "还款方式 0等额本金 1等额本息，2一次还本付息, 3按月付息，到期还本", required = false)
	private Integer borrowStyle;
	@ApiModelProperty(value = "收款银行名称", required = false)
	private String bankName;
	@ApiModelProperty(value = "收款账户名称", required = false)
	private String bankAccountName;
	@ApiModelProperty(value = "收款银行账号", required = false)
	private String bankNo;
	@ApiModelProperty(value = "借款合同编码", required = false)
	private String contractNo;

	@ApiModelProperty(value = "合同附件信息", required = false)
	//private Map<Integer,List<ContractInfoVO>> contractInfoMap=new HashMap<Integer, List<ContractInfoVO>>();
	private List<ContractInfoVO> contractInfoList = new ArrayList<ContractInfoVO>();

	
	@ApiModelProperty(value = "操作日志信息", required = false)
	private List<ApplyOperateLogVO> operatorLogList = new ArrayList<ApplyOperateLogVO>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getSocialCreditIdentifier() {
		return socialCreditIdentifier;
	}

	public void setSocialCreditIdentifier(String socialCreditIdentifier) {
		this.socialCreditIdentifier = socialCreditIdentifier;
	}

	public String getBusinessRegistrationNo() {
		return businessRegistrationNo;
	}

	public void setBusinessRegistrationNo(String businessRegistrationNo) {
		this.businessRegistrationNo = businessRegistrationNo;
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

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Integer getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(Integer borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

//	public Map<Integer, List<ContractInfoVO>> getContractInfoMap() {
//		return contractInfoMap;
//	}
//
//	public void setContractInfoMap(Map<Integer, List<ContractInfoVO>> contractInfoMap) {
//		this.contractInfoMap = contractInfoMap;
//	}

	public List<ApplyOperateLogVO> getOperatorLogList() {
		return operatorLogList;
	}

	public void setOperatorLogList(List<ApplyOperateLogVO> operatorLogList) {
		this.operatorLogList = operatorLogList;
	}

	public List<ContractInfoVO> getContractInfoList() {
		return contractInfoList;
	}

	public void setContractInfoList(List<ContractInfoVO> contractInfoList) {
		this.contractInfoList = contractInfoList;
	}
	
	
}
