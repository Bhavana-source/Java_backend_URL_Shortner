package com.shortenUrl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlResponseDto {
	public ShortUrlResponseDto(String originalUrl, String shortCode, String shortUrl) {
		this.originalUrl = originalUrl;
		this.shortCode = shortCode;
		this.shortUrl = shortUrl;
	}

	String originalUrl;
	String shortCode;
	String shortUrl;

}
