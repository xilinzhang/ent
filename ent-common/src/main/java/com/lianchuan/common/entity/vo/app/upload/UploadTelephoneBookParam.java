package com.lianchuan.common.entity.vo.app.upload;

import java.util.List;
import com.lianchuan.common.entity.vo.app.BaseUserParam;

/**
 * 上传电话簿请求参数
 */
public class UploadTelephoneBookParam extends BaseUserParam {

	/** 电话列表 */
	private List<PhoneVO> phoneList;

	public List<PhoneVO> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<PhoneVO> phoneList) {
		this.phoneList = phoneList;
	}

}
