package com.lianchuan.common.exception;

public class ParamException extends EntException {

	private static final long serialVersionUID = -3733125538951688339L;

	public ParamException(String message, String button) {
		super(message);
		setStatus(ERROR_PARAM);
		setButton(button);
	}

	public ParamException(String message) {
		super(message);
		setStatus(ERROR_PARAM);
	}

	public ParamException() {
		super(ERROR_PARAM, "参数错误");
	}
}
