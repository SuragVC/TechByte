package com.techbyte.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Admin;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.exception.LoginException;
import com.techbyte.repository.AdminDAO;
import com.techbyte.repository.AdminSessionDAO;

@Service
public class CurrentAdminSessionServiceImpl implements CurrentAdminSessionService{
	
	@Autowired
	private AdminSessionDAO sessionDAO;
	
	@Autowired
	private AdminDAO signUpDAO;
	
	@Override
	public CurrentSessionAdmin getCurrentAdminSession(String key) throws LoginException {
		List<CurrentSessionAdmin>adminList= sessionDAO.findAll();
		if(adminList.size()==1) {
			throw new LoginException("Already a active session is working please logout!");
		}
		Optional<CurrentSessionAdmin> currentSessionuser = sessionDAO.findByUuid(key);
		
		if(currentSessionuser.isPresent()) {
			return currentSessionuser.get();
		}else {
			throw new LoginException("UnAuthorized!!!");
		}
	}

	

	@Override
	public Admin getSignUpDetails(String key) throws LoginException {
		Optional<CurrentSessionAdmin> currentUser = sessionDAO.findByUuid(key);
		if(!currentUser.isPresent())
		{
			return null;
		}
		Integer SignUpUserId = currentUser.get().getId();
		return (signUpDAO.findById(SignUpUserId)).get();
	}



	@Override
	public Integer getCurrentAdminSessionId(String key) throws LoginException {
		Optional<CurrentSessionAdmin> currentAdmin = sessionDAO.findByUuid(key);
		if(!currentAdmin.isPresent())
		{
			throw new LoginException("UnAuthorized!!!");
		}
		return currentAdmin.get().getId();
	}

}
