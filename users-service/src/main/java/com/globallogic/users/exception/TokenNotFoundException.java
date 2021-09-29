package com.globallogic.users.exception;

public class TokenNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5447596140485916536L;

	public TokenNotFoundException() {
		super();

	}

	public TokenNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenNotFoundException(String message) {
		super(message);
	}

	public TokenNotFoundException(Throwable cause) {
		super(cause);
	}

}
