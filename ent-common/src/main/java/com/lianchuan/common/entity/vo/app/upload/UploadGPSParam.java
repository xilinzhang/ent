package com.lianchuan.common.entity.vo.app.upload;

import java.util.List;

import com.lianchuan.common.entity.vo.app.BaseUserParam;

/**
 * 上传GPS请求参数
 */
public class UploadGPSParam extends BaseUserParam {

	/** GPS信息 */
	private List<GPSInfoVO> gpsInfos;
	/** 平台标识,1:IOS,2:android,3:其他 */
	private int flag;

	public List<GPSInfoVO> getGpsInfos() {
		return gpsInfos;
	}

	public void setGpsInfos(List<GPSInfoVO> gpsInfos) {
		this.gpsInfos = gpsInfos;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
