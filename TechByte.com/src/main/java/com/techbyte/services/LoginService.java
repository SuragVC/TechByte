package com.techbyte.services;

import com.techbyte.entity.LogIn;
import com.techbyte.exception.LoginException;

public interface LoginService {
	
	public String logInAccount(LogIn loginData) throws LoginException;
	public String logOutFromAccount(String key) throws LoginException;
}
