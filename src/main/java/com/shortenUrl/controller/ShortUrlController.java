package com.shortenUrl.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortenUrl.dto.ShortUrlRequestDto;
import com.shortenUrl.dto.ShortUrlResponseDto;
import com.shortenUrl.service.ShortCodeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class ShortUrlController {
	private final ShortCodeService service;

	public ShortUrlController(ShortCodeService service) {
		this.service = service;
	}

	@PostMapping("/generateShortUrl")
	public ResponseEntity<ShortUrlResponseDto> createShortUrl(@RequestBody @Valid ShortUrlRequestDto dto) {
		ShortUrlResponseDto result = service.createShortUrl(dto);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{shortCode}")
	public ResponseEntity<String> redirectUrl(@PathVariable String shortCode){
		String originalUrl = service.getOriginalUrl(shortCode);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(originalUrl));
		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}

}
