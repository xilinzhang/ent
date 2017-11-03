package com.lianchuan.common.entity.po.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;

/**
 * 老数据提供
 *
 */
@Entity
@Table(name = DaoGlobal.ENT_TABLE_BASE_NAME + "apply_info_provide")
public class EntApplyInfoProvidePO extends BasePO {

	private static final long serialVersionUID = -8180359589917734970L;

	/** 全称或简称 */
	@Column(nullable = false, length = 255)
	private String name;

	/** 法定代表人 */
	@Column(nullable = false, length = 255)
	private String legalRepresentative;

	/** 机构联系人手机 */
	@Column(nullable = false, length = 255)
	private String contactPhone;

	/** 统一社会信用代码 */
	@Column(nullable = false, length = 50)
	private String socialCreditIdentifier;

	/** 营业执照代码 */
	@Column(nullable = false, length = 50)
	private String businessRegistrationNo;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
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
}
