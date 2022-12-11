package com.techbyte.services;

import com.techbyte.entity.Admin;
import com.techbyte.exception.LoginException;

public interface AdminService {
	public Admin createNewAdminUp(Admin signUp) throws LoginException;;
	
	
}
