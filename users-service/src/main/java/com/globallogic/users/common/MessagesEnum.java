package com.globallogic.users.common;

public enum MessagesEnum {
	
	MSG_EMAIL_EXISTS("El correo ya registrado"),
	MSG_TOKEN_NOT_FOUND("El token no se pudo obtener");
	
	private MessagesEnum(String message){
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return message;
	}
}
