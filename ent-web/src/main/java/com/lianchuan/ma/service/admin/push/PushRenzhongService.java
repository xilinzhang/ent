package com.lianchuan.ma.service.admin.push;

import org.apache.commons.lang3.tuple.Pair;

public interface PushRenzhongService {

	
	/**
	 * 推送数据
	 * @param applyId
	 * @return
	 */
	public Pair<Boolean, String> pushData(Long applyId);
	
	
}
