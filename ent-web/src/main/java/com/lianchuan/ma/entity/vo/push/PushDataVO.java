package com.lianchuan.ma.entity.vo.push;

public class PushDataVO {

	private PushEntAccountVO account;
	private PushEntCompanyVO company;
	private PushEntContractVO contract;
	private PushEntExtendVO extend;
	private String notify_url;
	private PushEntOrderVO order;

	public PushEntOrderVO getOrder() {
		return order;
	}

	public void setOrder(PushEntOrderVO order) {
		this.order = order;
	}

	public PushEntExtendVO getExtend() {
		return extend;
	}

	public void setExtend(PushEntExtendVO extend) {
		this.extend = extend;
	}

	public PushEntCompanyVO getCompany() {
		return company;
	}

	public void setCompany(PushEntCompanyVO company) {
		this.company = company;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public PushEntAccountVO getAccount() {
		return account;
	}

	public void setAccount(PushEntAccountVO account) {
		this.account = account;
	}

	public PushEntContractVO getContract() {
		return contract;
	}

	public void setContract(PushEntContractVO contract) {
		this.contract = contract;
	}

}
