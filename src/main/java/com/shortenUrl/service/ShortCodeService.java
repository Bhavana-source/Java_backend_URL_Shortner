package com.shortenUrl.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.shortenUrl.Mapper.ShortUrlMapper;
import com.shortenUrl.dto.ShortUrlDto;
import com.shortenUrl.dto.ShortUrlRequestDto;
import com.shortenUrl.dto.ShortUrlResponseDto;
import com.shortenUrl.entity.ShortUrlEntity;
import com.shortenUrl.exception.URLException;
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

		Optional<ShortUrlEntity> existing = shortUrlRepo.findByOriginalUrl(request.getOriginalUrl());

		if (existing.isPresent()) {
			return mapper.toResponse(existing.get());
		}

		String shortCode = (request.getCustomCode() != null) ? request.getCustomCode()
				: shortCodeGenerator.randomString();

		ShortUrlEntity entity = mapper.toEntity(request, shortCode);

		try {
			ShortUrlEntity saved = shortUrlRepo.save(entity);
			return mapper.toResponse(saved);

		} catch (DataIntegrityViolationException e) {

			if (request.getCustomCode() != null) {
				throw new URLException("Custom alias code already exists");
			}

			throw new RuntimeException("Collision occurred, please retry");
		}
	}

	@Cacheable(value = "urls", key = "#shortCode", unless = "#result == null")
	public String getOriginalUrl(String shortCode) {
		log.info("DB HIT for {}", shortCode);
		ShortUrlEntity entity = shortUrlRepo.findByShortCode(shortCode)
				.orElseThrow(() -> new URLException("Short URL not found"));
		if (entity.getExpiresAt() != null && entity.getExpiresAt().isBefore(LocalDateTime.now())) {

			throw new URLException("Short URL has expired");
		}
		return entity.getOriginalUrl();
	}

	public void incrementCount(String shortCode) {
		log.info("increment click count", shortCode);
		ShortUrlEntity entity = shortUrlRepo.findByShortCode(shortCode)
				.orElseThrow(() -> new URLException("Short URL not found"));
		entity.setClickCount(entity.getClickCount() + 1);
		shortUrlRepo.save(entity);
	}

	public String generateUniqueShortCode(String customCode) {

		if (customCode != null && !customCode.isBlank()) {
			return customCode;
		}

		return shortCodeGenerator.randomString();
	}
	
	public ShortUrlDto getStats(String shortCode) {

	    ShortUrlEntity entity = shortUrlRepo.findByShortCode(shortCode)
	            .orElseThrow(() -> new URLException("Short URL not found"));

	    ShortUrlDto response = new ShortUrlDto();

	    response.setShortCode(entity.getShortCode());
	    response.setOriginalUrl(entity.getOriginalUrl());
	    response.setClickCount(entity.getClickCount());
	    response.setCreatedAt(entity.getCreatedAt());
	    response.setExpiresAt(entity.getExpiresAt());
	    response.setIsActive(entity.getIsActive());

	    return response;
	}
	
	public void deactivateUrl(String shortCode) {

	    ShortUrlEntity entity = shortUrlRepo.findByShortCode(shortCode)
	            .orElseThrow(() -> new URLException("Short URL not found"));

	    
	    if (Boolean.FALSE.equals(entity.getIsActive())) {
	        throw new URLException("already inactive");
	    }

	    entity.setIsActive(false);
	    shortUrlRepo.save(entity);
	}
}
