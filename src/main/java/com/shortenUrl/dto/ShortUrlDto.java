package com.shortenUrl.dto;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlDto {
	
	long id;
	
	String originalUrl;
	
	String shortCode;
	
	Timestamp createdAt;
	
	Timestamp expiresAt;
	
	long clickCount;
	
	Boolean isActive;

}
