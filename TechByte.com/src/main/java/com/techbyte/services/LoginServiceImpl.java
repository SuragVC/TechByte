package com.techbyte.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.CurrentSessionUser;
import com.techbyte.entity.LogIn;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.entity.User;
import com.techbyte.entity.UserStatus;
import com.techbyte.exception.LoginException;
import com.techbyte.repository.LogInDAO;
import com.techbyte.repository.SessionDAO;
import com.techbyte.repository.UserDAO;
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private UserDAO signUpDAO;
	
	@Autowired
	private SessionDAO sessionDao;
	
	@Autowired
	private CurrentUserSessionService getCurrentLoginUserSession;
	
	@Autowired
	private LogInDAO loginDAO;
	
	

	@Override
	public String logInAccount(LogIn loginData) throws LoginException {
		System.out.println(loginData.getMobileNo());
		List<CurrentSessionUser>list = sessionDao.findAll();
		if(list.size()>=1){
			throw new LoginException("Already a user loged in Log out first then try to logIn");
		}
		Optional<User> options = signUpDAO.findByMobileNo(loginData.getMobileNo());
		if(options.isEmpty()) {
			throw new LoginException("Invalid mobile Number ");
		}
		if(options.get().getStatus()==UserStatus.NOT_ACTIVE) {
			throw new LoginException("User not Validated!,Please complete Email Verification");
		}
		User newSignUp = options.get();
		
		Optional<CurrentSessionUser> currentSessionUser = sessionDao.findByMobileNo(newSignUp.getMobileNo());
		
		
		if(!currentSessionUser.isEmpty()) {
			throw new LoginException("User already login with this userId");
		}
		
		if((newSignUp.getMobileNo().equals(loginData.getMobileNo()))  && newSignUp.getPassword().equals(loginData.getPassword())) {
			
			String key = RandomIdGenerator.getRandomStringSessionId();		
			CurrentSessionUser currentSessionUser2 = new CurrentSessionUser(key, newSignUp.getMobileNo(),LocalDateTime.now());
			currentSessionUser2.setUserId(newSignUp.getUserId());
			loginDAO.save(loginData);
			sessionDao.save(currentSessionUser2);
			return currentSessionUser2.toString();
		}else {
			throw new LoginException("Invalid mobile and Password");
		}
		
	}
	

	@Override
	public String logOutFromAccount(String key) throws LoginException {
		
		Optional<CurrentSessionUser> currentSessionuserOptional = sessionDao.findByUuid(key);
		
		if(currentSessionuserOptional.isEmpty()) {
			throw new LoginException("User has not loged in with this Userid");
		}
		sessionDao.delete(currentSessionuserOptional.get());
		
		LogIn logindata = loginDAO.findByMobileNo(currentSessionuserOptional.get().getMobileNo());
		
		loginDAO.delete(logindata);
		
		return "Logged Out Succefully....";
	}
	

}
