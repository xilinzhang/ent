package com.lianchuan.common.entity.vo.app.upload;

/**
 * GPS信息
 */
public class GPSInfoVO {

	/** 经度 */
	private Double longitude;
	/** 纬度 */
	private Double latitude;
	/** 记录时间 */
	private Long time;
	/** 操作 */
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
