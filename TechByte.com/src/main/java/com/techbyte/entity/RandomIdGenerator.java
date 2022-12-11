package com.techbyte.entity;

import java.util.Random;

public class RandomIdGenerator {
	public static Integer getRandomInteger() {
		Random random = new Random();
		int number = random.nextInt();
		return  Math.abs(number);
	}
	public static String getRandomStringSessionId() {
		int min = 10001;
	    int max = 99999;
		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
		return ""+random_int;
	}
	public static Integer getRandomProductId() {
		int min = 1001;
	    int max = 9999;
		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
		return random_int;
	}
	public static Integer getHighLegthID() {
		int min = 1111111;
	    int max = 9999999;
		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
		return random_int;
	}
	public static Integer getOneTimePassword() {
		int min = 111111;
	    int max = 999999;
		int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
		return random_int;
	}
}
