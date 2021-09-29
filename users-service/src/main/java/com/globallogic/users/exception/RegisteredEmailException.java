package com.globallogic.users.exception;

public class RegisteredEmailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5447596140485916536L;

	public RegisteredEmailException() {
		super();

	}

	public RegisteredEmailException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RegisteredEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisteredEmailException(String message) {
		super(message);
	}

	public RegisteredEmailException(Throwable cause) {
		super(cause);
	}

}
