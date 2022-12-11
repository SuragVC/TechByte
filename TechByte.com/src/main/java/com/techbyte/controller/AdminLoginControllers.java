package com.techbyte.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.AdminLogIn;
import com.techbyte.exception.LoginException;
import com.techbyte.services.AdminLoginService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminLoginControllers {
	
	@Autowired
	private AdminLoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginHandler(@Valid @RequestBody AdminLogIn loginData) throws LoginException {
		String login = loginService.logInAccount(loginData);
		return new ResponseEntity<String>(login,HttpStatus.OK);
	}
	
	@PatchMapping("/logout")
	public ResponseEntity<String> logOutFromAccount(@RequestParam String key) throws LoginException{
		String logout = loginService.logOutFromAccount(key);
		return new ResponseEntity<String>(logout,HttpStatus.OK);
	}
	
}
