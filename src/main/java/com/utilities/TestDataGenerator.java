package com.utilities;


import java.util.Random;

import com.github.javafaker.Faker;

public class TestDataGenerator extends BaseFunctions{

	Faker objFaker = new Faker(); 
	
	public static int getRandomNumberBetweenRange(int low, int high) {
		Random r = new Random();
		int result = 0;
		int lowNum = low ;
		int highNum = high;

		if(low!=high) {
			result = r.nextInt(highNum-lowNum) + low;
			return result;
		}else {
			result =low;
		}
		return low;
	}
	
	public static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	public static String getRandomCharacterLimit(int max) {
		StringBuilder newStrBuilder = new StringBuilder();
		Random rnd = new Random();
		while ( newStrBuilder.length() < max) { // length of the random string.
			int index = (int) (rnd.nextFloat() * chars.length());
			newStrBuilder.append(chars.charAt(index));
		}
		String newStr =  newStrBuilder.toString();
		return newStr;

	}
	
	public String getRandomEmail() {
		return getFirstName()+"@mailinator.com";
	}
	
	public String getRandomMobileNo() {
		return objFaker.phoneNumber().cellPhone();
	}
	
	public String getFirstName() {
		return objFaker.name().firstName();
	}
	
	public String getFullName() {
		return objFaker.name().fullName();
	}
	
	public String getLastName() {
		return objFaker.name().lastName();
	}
	
	public String getfullAddress() {
		return objFaker.address().fullAddress();
	}
}
