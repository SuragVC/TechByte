package com.techbyte.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Admin;
import com.techbyte.entity.AdminLogIn;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.exception.LoginException;
import com.techbyte.repository.AdminDAO;
import com.techbyte.repository.AdminLogInDAO;
import com.techbyte.repository.AdminSessionDAO;


@Service
public class AdminLoginServiceImpl implements AdminLoginService{

	@Autowired
	private AdminDAO signUpDAO;
	
	@Autowired
	private AdminSessionDAO SessionDAO;
	
	@Autowired
	private CurrentAdminSessionService getCurrentLoginUserSession;
	
	@Autowired
	private AdminLogInDAO loginDAO;
	
	

	@Override
	public String logInAccount(AdminLogIn loginData) throws LoginException {

		Optional<Admin> options = signUpDAO.findByMobileNo(loginData.getAdminMobileNo());
		
		if(!options.isPresent()) {
			throw new LoginException("Invalid mobile Number ");
		}
		
		Admin newSignUp = options.get();

		Integer newSignUpId = newSignUp.getAdminId();
		Optional<CurrentSessionAdmin> currentSessionUser = SessionDAO.findByAdminId(newSignUpId);
		
		if(currentSessionUser.isPresent()) {
			throw new LoginException("Already an admin is active,Logout then try to login");
		}
		
		if((newSignUp.getMobileNo().equals(loginData.getAdminMobileNo()))  && newSignUp.getPassword().equals(loginData.getPassword())) {
			String key = RandomString.getRandomString();
			
			CurrentSessionAdmin currentSessionAdmin2 = new CurrentSessionAdmin();
			currentSessionAdmin2.setAdminId(newSignUpId);
			currentSessionAdmin2.setMobileNo(newSignUp.getMobileNo());
			currentSessionAdmin2.setUuid(key);
			currentSessionAdmin2.setLocalDateTime(LocalDateTime.now());
			
			loginData.setLoginTime(LocalDateTime.now());
			loginData.setAdminId(newSignUpId);
			loginDAO.save(loginData);
			SessionDAO.save(currentSessionAdmin2);
			return currentSessionAdmin2.toString();
		}else {
			throw new LoginException("Invalid mobile and Password");
		}
		
	}
	

	@Override
	public String logOutFromAccount(String key) throws LoginException {
		System.out.println("working 2");
		Optional<CurrentSessionAdmin> currentSessionuserOptional = SessionDAO.findByUuid(key);
		
		if(currentSessionuserOptional.isEmpty()) {
			throw new LoginException("User has not looged in with this Userid");
		}
		
		
		SessionDAO.delete(currentSessionuserOptional.get());
		
		Optional<AdminLogIn> logindata = loginDAO.findByAdminId(currentSessionuserOptional.get().getAdminId());
		
		loginDAO.delete(logindata.get());
		
		return "Logged Out Succefully....";
	}
	

}
