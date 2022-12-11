package com.techbyte.services;

import java.util.List;

import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OTPValidataionException;
import com.techbyte.exception.UserNotFoundException;

public interface UserService {
	public User createNewSignUp(User signUp) throws LoginException;
	public User createNewSignUpWithGmailValidation(User signUp) throws LoginException,OTPValidataionException;
	public User updateSignUpDetails(User signUp,String key) throws LoginException;
	public String otpValidation(String gmail,Integer OTP)throws OTPValidataionException,LoginException;
}
