package com.techbyte.services;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Admin;
import com.techbyte.exception.LoginException;
import com.techbyte.repository.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminDAO signUpDAO;
	
	
	@Override
	public Admin createNewAdminUp(Admin signUp) throws LoginException {
		System.out.println(signUp.getMobileNo());
		Optional<Admin> opt = signUpDAO.findByMobileNo(signUp.getMobileNo());
		if(opt.isPresent())
		{
			throw new LoginException("Admin Already Exist!");
		}
		
		return signUpDAO.save(signUp);
	}
}
