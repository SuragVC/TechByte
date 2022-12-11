package com.techbyte.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.OTPValidataionException;
import com.techbyte.services.UserService;



@CrossOrigin(origins = "*")
@RestController
public class SignUpController {
	
	@Autowired
	private UserService signUpService;
	
	@PostMapping("/signUp")
	public ResponseEntity<User> createNewSignUpHandler(@Valid @RequestBody User newSignUp) throws LoginException {
		User newSignedUp =signUpService.createNewSignUp(newSignUp);
		return new ResponseEntity<User>(newSignedUp,HttpStatus.CREATED);

	}
	
	@PutMapping("/signUp/update")
	public ResponseEntity<User> updateSignUpDetailsHandler(@Valid @RequestBody User signUp, @RequestParam String key) throws LoginException{
		User newUpdatedSignUp = signUpService.updateSignUpDetails(signUp,key);
		return new ResponseEntity<User>(newUpdatedSignUp,HttpStatus.ACCEPTED);
	}
	@PostMapping("/signUp/valid/{gmail}/{otp}")
	public ResponseEntity<String> validateOtp(@RequestParam String gmail,@RequestParam Integer otp) throws LoginException, OTPValidataionException {
		int otpCheck=(int)otp;
		if(!(otpCheck>=111111 && otpCheck<=999999)) {
			throw new OTPValidataionException("OTP SHULD BE 6 DIGITS");
		}
		String ans = signUpService.otpValidation(gmail, otp);
		return new ResponseEntity<String>(ans,HttpStatus.OK);
	}
	@PostMapping("/signUp/valid")
	public ResponseEntity<User> createNewSignUpWithGmailValidation(@Valid @RequestBody User newSignUp) throws LoginException, OTPValidataionException {
		User newSignedUp =signUpService.createNewSignUpWithGmailValidation(newSignUp);
		return new ResponseEntity<User>(newSignedUp,HttpStatus.CREATED);

	}
}
