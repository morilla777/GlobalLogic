package com.globallogic.users.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserMessage {

	private User user;
	private String id;
	private Date created;
	private Date modified;
	private Date lastLogin;
	private String token;
	private Boolean isActive;
	
	public RegisterUserMessage() {
		
	}
	
	public RegisterUserMessage(User user) {
		this.user = user;
	}

}
