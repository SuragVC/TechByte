package com.techbyte.services;

import com.techbyte.entity.CurrentSessionUser;
import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;

public interface CurrentUserSessionService {
	public CurrentSessionUser getCurrentUserSession(String userKey) throws LoginException;
	public Integer getCurrentUserSessionId(String key) throws LoginException;
	public User getSignUpDetails(String key) throws LoginException;;
}
