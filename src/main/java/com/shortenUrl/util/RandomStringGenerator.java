package com.shortenUrl.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomStringGenerator {

	public String randomString() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random rnd = new Random();
		int length = rnd.nextBoolean() ? 6 : 7;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}

}
