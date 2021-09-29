package com.globallogic.users.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.users.bean.RegisterUserMessage;
import com.globallogic.users.bean.User;
import com.globallogic.users.common.MessagesEnum;
import com.globallogic.users.entity.Phones;
import com.globallogic.users.entity.Users;
import com.globallogic.users.exception.RegisteredEmailException;
import com.globallogic.users.repository.PhonesRepository;
import com.globallogic.users.repository.UsersRepository;
import com.globallogic.users.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PhonesRepository phonesRepository;
	
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public RegisterUserMessage register(User user, String token) throws RegisteredEmailException{
		
		if(this.existsUsersWithEmail(user.getEmail())) {
			throw new RegisteredEmailException(MessagesEnum.MSG_EMAIL_EXISTS.getMessage());
		}
		
		Users userEntity = new Users();
		Date now = new Date();
		userEntity.setId(UUID.randomUUID().toString());
		userEntity.setName(user.getName());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(user.getPassword());
		userEntity.setCreated(now);
		userEntity.setLastLogin(now);
		userEntity.setIsActive(Boolean.TRUE);
		userEntity.setToken(token);
		
		usersRepository.saveAndFlush(userEntity);
		
		user.getPhones().forEach(phone->{
			Phones phoneEntity = new Phones();
			phoneEntity.setNumber(phone.getNumber());
			phoneEntity.setCitycode(phone.getCitycode());
			phoneEntity.setCountrycode(phone.getCountrycode());
			phoneEntity.setUser(userEntity);
			
			phonesRepository.saveAndFlush(phoneEntity);
		});
		
		final RegisterUserMessage registerUserMessage = new RegisterUserMessage(user);
		registerUserMessage.setId(userEntity.getId());
		registerUserMessage.setCreated(userEntity.getCreated());
		registerUserMessage.setLastLogin(userEntity.getLastLogin());
		registerUserMessage.setIsActive(userEntity.getIsActive());
		registerUserMessage.setToken(userEntity.getToken());
		
		return registerUserMessage;
		
	}

	@Override
	public Boolean existsUsersWithEmail(String email) {
		
		Query query = em.createNamedQuery("User.countUsersByEmail");
		query.setParameter("email", email);
		Long qty = (Long)query.getSingleResult();
		
		return qty>0L;
	}

}
