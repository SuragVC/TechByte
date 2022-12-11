package com.techbyte.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.Admin;
import com.techbyte.exception.LoginException;
import com.techbyte.services.AdminService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminSignUpController {
	
	@Autowired
	private AdminService signUpService;
	 
	@PostMapping("/signUp")
	public ResponseEntity<Admin> createNewSignUpHandler(@Valid @RequestBody Admin  newSignUp) throws LoginException {
		Admin newSignedUp =signUpService.createNewAdminUp(newSignUp);
		return new ResponseEntity<Admin >(newSignedUp,HttpStatus.CREATED);

	}
}
