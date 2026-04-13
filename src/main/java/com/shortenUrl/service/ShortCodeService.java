package com.shortenUrl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shortenUrl.Mapper.ShortUrlMapper;
import com.shortenUrl.dto.ShortUrlRequestDto;
import com.shortenUrl.dto.ShortUrlResponseDto;
import com.shortenUrl.entity.ShortUrlEntity;
import com.shortenUrl.repository.ShortUrlRepo;
import com.shortenUrl.util.RandomStringGenerator;

@Service
public class ShortCodeService {

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
	
	public String getOriginalUrl(String shortCode) {
		ShortUrlEntity entity = shortUrlRepo.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("Short URL not found"));
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
