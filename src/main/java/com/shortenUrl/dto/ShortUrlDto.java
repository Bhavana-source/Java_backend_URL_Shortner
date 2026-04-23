package com.shortenUrl.dto;

import java.security.Timestamp;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlDto {
	
	long id;
	
	String originalUrl;
	
	String shortCode;
	
	LocalDateTime createdAt;
	
	LocalDateTime expiresAt;
	
	long clickCount;
	
	Boolean isActive;

}
