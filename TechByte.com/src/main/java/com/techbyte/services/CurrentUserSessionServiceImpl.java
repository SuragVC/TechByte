package com.techbyte.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.CurrentSessionUser;
import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;
import com.techbyte.repository.SessionDAO;
import com.techbyte.repository.UserDAO;

@Service
public class CurrentUserSessionServiceImpl implements CurrentUserSessionService{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private UserDAO signUpDAO;
	
	@Override
	public CurrentSessionUser getCurrentUserSession(String userKey) throws LoginException {
		
		Optional<CurrentSessionUser> currentSessionuser = sessionDAO.findByUuid(userKey);
		
		if(currentSessionuser.isPresent()) {
			return currentSessionuser.get();
		}else {
			throw new LoginException("Un Authorised!!!");
		}
	}
	
	@Override
	public Integer getCurrentUserSessionId(String key) throws LoginException {
		Optional<CurrentSessionUser> currentUser = sessionDAO.findByUuid(key);
		if(!currentUser.isPresent())
		{
			throw new LoginException("UnAuthorized!!!");
		}
		return currentUser.get().getId();
	}

	@Override
	public User getSignUpDetails(String key) throws LoginException {
		Optional<CurrentSessionUser> currentUser = sessionDAO.findByUuid(key);
		if(!currentUser.isPresent())
		{
			return null;
		}
		Integer SignUpUserId = currentUser.get().getId();
		System.out.println(SignUpUserId );
		
		return (signUpDAO.findById(SignUpUserId)).get();
	}

}
