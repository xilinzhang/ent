package com.lianchuan.common.exception;

public class EntException extends RuntimeException implements ErrorId {

	private static final long serialVersionUID = 2718214883948958952L;

	private int status = ErrorId.ERROR_UNKNOWN;

	private String button = null;

	private String htmlDes = null;

	public EntException() {
		super();
	}

	public EntException(int status, String message) {
		super(message);
		this.status = status;
	}

	public EntException(int status, String message, String button) {
		super(message);
		this.status = status;
		this.button = button;
	}

	public EntException(int status, String message, String button, String htmlDes) {
		super(message);
		this.status = status;
		this.button = button;
		this.htmlDes = htmlDes;
	}

	public EntException(String message) {
		super(message);
	}

	public EntException(Throwable cause) {
		super(cause);
	}

	public EntException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getHtmlDes() {
		return htmlDes;
	}

	public void setHtmlDes(String htmlDes) {
		this.htmlDes = htmlDes;
	}

}
