package com.lianchuan.common.entity.po.ent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;

/***
 * 企业工商信息申请提交
 */
@Entity
@Table(name = DaoGlobal.ENT_TABLE_BASE_NAME + "apply")
public class EntApplyPO extends BasePO {

	private static final long serialVersionUID = -8180359589917734970L;

	/** 全称或简称 */
	@Column(nullable = false, length = 255)
	private String name;

	/** 法定代表人 */
	@Column(nullable = false, length = 255)
	private String legalRepresentative;

	/** 联系方式 */
	@Column(nullable = false, length = 255)
	private String contactPhone;

	/** 统一社会信用代码 */
	@Column(nullable = false, length = 50)
	private String socialCreditIdentifier;

	/** 营业执照代码 */
	@Column(nullable = false, length = 50)
	private String businessRegistrationNo;

	/** 注册资本（万元） */
	@Column(nullable = false, length = 50)
	private String registMoney;

	/** 注册地址 */
	@Column(nullable = false, length = 255)
	private String address;

	/** 成立时间 */
	@Column(nullable = false)
	private Date foundDate;

	/** 借款用途 */
	@Column(nullable = false, length = 255)
	private String loanUsage;

	/** 借款金额（元） */
	@Column(nullable = false, length = 50)
	private String loanMoney;
	
	/** 借款日期 */
	@Column(nullable = false)
	private Date loanDate;
	
	/** 借款期限 */
	@Column(nullable = false)
	private Integer loanPeriod;
	
	/** 还款方式 1一次性还本付息，2按月付息到期还本 */
	@Column(nullable = false)
	private Integer borrowStyle;
	
	/** 收款银行名称 */
	@Column(nullable = false, length = 255)
	private String bankName;
	
	/** 收款账户名称 */
	@Column(nullable = false, length = 255)
	private String bankAccountName;
	
	/** 收款银行账号 */
	@Column(nullable = false, length = 255)
	private String bankNo;
	
	/** 借款合同编码 */
	@Column(nullable = false, length = 255)
	private String contractNo;

	/** 创建人 */
	@Column
	private Long createManagerId;

	/** 编号 */
	@Column(nullable = false,unique = true,length = 255)
	private String code;

	/** 标的状态 {@link com.lianchuan.common.entity.type.ent.ApplyStatus} */
	@Column(nullable = false)
	private Integer applyStatus;
	
	/** 修改时间 */
	@Column(nullable = false)
	private Date modifyDate = new Date();

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

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getLoanUsage() {
		return loanUsage;
	}

	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
	}

	public Long getCreateManagerId() {
		return createManagerId;
	}

	public void setCreateManagerId(Long createManagerId) {
		this.createManagerId = createManagerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
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

}
