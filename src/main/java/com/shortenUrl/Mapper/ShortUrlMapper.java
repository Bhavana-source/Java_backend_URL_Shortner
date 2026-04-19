package com.shortenUrl.Mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.shortenUrl.dto.ShortUrlRequestDto;
import com.shortenUrl.dto.ShortUrlResponseDto;
import com.shortenUrl.entity.ShortUrlEntity;

@Component
public class ShortUrlMapper {

	public ShortUrlEntity toEntity(ShortUrlRequestDto request, String shortCode) {
		ShortUrlEntity shortUrl = new ShortUrlEntity();
		shortUrl.setOriginalUrl(request.getOriginalUrl());
		shortUrl.setShortCode(shortCode);
		shortUrl.setCreatedAt(LocalDateTime.now());
		shortUrl.setClickCount(0L);
		shortUrl.setIsActive(true);
		shortUrl.setExpiresAt(null);
		return shortUrl;
	}

	public ShortUrlResponseDto toResponse(ShortUrlEntity shortUrl) {
		String shortUrlLink = "http://localhost:8080/api/" + shortUrl.getShortCode();

		return new ShortUrlResponseDto(shortUrl.getOriginalUrl(), shortUrl.getShortCode(), shortUrlLink);
	}

}
