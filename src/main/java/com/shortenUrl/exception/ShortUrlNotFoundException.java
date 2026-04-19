package com.shortenUrl.exception;

public class ShortUrlNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ShortUrlNotFoundException(String message) {
		super(message);
	}

}
