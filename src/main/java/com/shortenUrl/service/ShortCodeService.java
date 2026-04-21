package com.shortenUrl.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.shortenUrl.Mapper.ShortUrlMapper;
import com.shortenUrl.dto.ShortUrlRequestDto;
import com.shortenUrl.dto.ShortUrlResponseDto;
import com.shortenUrl.entity.ShortUrlEntity;
import com.shortenUrl.exception.ShortUrlNotFoundException;
import com.shortenUrl.repository.ShortUrlRepo;
import com.shortenUrl.util.RandomStringGenerator;

@Service
public class ShortCodeService {
	
	private static final Logger log = LoggerFactory.getLogger(ShortCodeService.class);
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	private final ShortUrlRepo shortUrlRepo;
	private final RandomStringGenerator shortCodeGenerator;
	private final ShortUrlMapper mapper;

	public ShortCodeService(ShortUrlRepo shortUrlRepo, RandomStringGenerator shortCodeGenerator,
			ShortUrlMapper mapper) {
		this.shortUrlRepo = shortUrlRepo;
		this.shortCodeGenerator = shortCodeGenerator;
		this.mapper = mapper;
	}

	public ShortUrlResponseDto createShortUrl(ShortUrlRequestDto request) {
		Optional<ShortUrlEntity> urlExists = shortUrlRepo.findByOriginalUrl(request.getOriginalUrl());
		if(urlExists.isPresent()) {
			return mapper.toResponse(urlExists.get());
		}

		String shortCode = generateUniqueShortCode();

		ShortUrlEntity entity = mapper.toEntity(request, shortCode);
		ShortUrlEntity saved = shortUrlRepo.save(entity);

		return mapper.toResponse(saved);

	}
	
	@Cacheable(value = "urls", key = "#shortCode", unless = "#result == null")
	public String getOriginalUrl(String shortCode) {
		log.info("DB HIT for {}", shortCode);
		ShortUrlEntity entity = shortUrlRepo.findByShortCode(shortCode).orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found"));
		entity.setClickCount(entity.getClickCount() + 1);
		shortUrlRepo.save(entity);
		return entity.getOriginalUrl();
	}

	public String generateUniqueShortCode() {
		String shortCode;
		do {
			shortCode = shortCodeGenerator.randomString();
		} while (shortUrlRepo.existsByShortCode(shortCode));
		return shortCode;
	}

}
