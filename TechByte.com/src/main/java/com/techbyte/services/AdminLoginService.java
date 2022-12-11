package com.techbyte.services;

import com.techbyte.entity.AdminLogIn;
import com.techbyte.exception.LoginException;

import ch.qos.logback.core.LogbackException;

public interface AdminLoginService {
	
	public String logInAccount(AdminLogIn loginData) throws LogbackException, LoginException;
	public String logOutFromAccount(String key) throws LoginException;
}
