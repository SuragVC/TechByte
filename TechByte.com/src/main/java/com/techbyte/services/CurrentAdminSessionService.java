package com.techbyte.services;

import com.techbyte.entity.Admin;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.exception.LoginException;

public interface CurrentAdminSessionService {
	public CurrentSessionAdmin getCurrentAdminSession(String key) throws LoginException;
	public Admin  getSignUpDetails(String key) throws LoginException;
	public Integer getCurrentAdminSessionId(String key) throws LoginException;
}
