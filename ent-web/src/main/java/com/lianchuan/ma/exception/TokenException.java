package com.lianchuan.ma.exception;

import com.lianchuan.common.exception.EntException;

public class TokenException extends EntException {

	private static final long serialVersionUID = -7581162909970347978L;

	public TokenException(String message) {
		super(message);
		setStatus(ERROR_TOKEN);
	}

}
