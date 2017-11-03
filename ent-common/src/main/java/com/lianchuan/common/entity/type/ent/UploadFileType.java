package com.lianchuan.common.entity.type.ent;

/**
 * 文件上传类型
 */
public enum UploadFileType {

	/** jpg */
	JPG(1, "jpg", "application/x-jpg"),
	/** jpeg */
	JPEG(2, "jpeg", "application/x-jpg"),
	/** png */
	PNG(3, "png", "application/x-png"),
	/** pdf */
	PDF(4, "pdf", "application/pdf"),
	/** xls */
	XLS(5, "xls", "application/vnd.ms-excel"),
	/** xlsx */
	XLSX(6, "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private int value;

	private String name;
	
	private String contentType;

	private UploadFileType(int value, String name, String contentType) {
		this.value = value;
		this.name = name;
		this.contentType = contentType;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public String getContentType() {
		return contentType;
	}

	public static UploadFileType getType(int value) {
		for (UploadFileType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}
	
	public static int getValue(String name) {
		for (UploadFileType type : values()) {
			if (type.getName().equals(name) ) {
				return type.getValue();
			}
		}
		return 0;
	}

}
