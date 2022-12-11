package com.techbyte.exception;

public class UserNotLogedinException extends Exception {

	
	public UserNotLogedinException() {}

	public UserNotLogedinException(String message) {
		super(message);
	}
	
}
