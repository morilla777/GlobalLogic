package com.globallogic.users.service;

import com.globallogic.users.bean.RegisterUserMessage;
import com.globallogic.users.bean.User;
import com.globallogic.users.exception.RegisteredEmailException;

public interface UserService {
	
	public RegisterUserMessage register(User user, String token) throws RegisteredEmailException;
	public Boolean existsUsersWithEmail(String email);
	
}
