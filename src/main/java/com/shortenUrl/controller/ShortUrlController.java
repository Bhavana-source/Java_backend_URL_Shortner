package com.shortenUrl.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortenUrl.dto.ShortUrlDto;
import com.shortenUrl.dto.ShortUrlRequestDto;
import com.shortenUrl.dto.ShortUrlResponseDto;
import com.shortenUrl.service.ClickEventProducer;
import com.shortenUrl.service.ShortCodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "URL Shortener API", description = "Operations for URL shortening")
@RestController
@RequestMapping("api")
public class ShortUrlController {
	private final ShortCodeService service;
	private final ClickEventProducer clickEventProducer;

	public ShortUrlController(ShortCodeService service, ClickEventProducer clickEventProducer) {
		this.service = service;
		this.clickEventProducer = clickEventProducer;
	}
	
	@Operation(summary = "Create short URL", description = "Generates a short URL from a long URL")
	@PostMapping("/generateShortUrl")
	public ResponseEntity<ShortUrlResponseDto> createShortUrl(@RequestBody @Valid ShortUrlRequestDto dto) {
		ShortUrlResponseDto result = service.createShortUrl(dto);
		return ResponseEntity.ok(result);
	}
	
	@Operation(summary = "Redirect to original URL", description = "Redirects to the original URL using short code")
//	@GetMapping("/{shortCode}")
//	public ResponseEntity<String> redirectUrl(@PathVariable String shortCode){
//		String originalUrl = service.getOriginalUrl(shortCode);
//		service.incrementCount(shortCode);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(URI.create(originalUrl));
//		return new ResponseEntity<>(headers, HttpStatus.FOUND);
//	}
	
	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirectUrl(@PathVariable String shortCode){

	    String originalUrl = service.getOriginalUrl(shortCode);

	    // 🔥 send event instead of DB update
	    clickEventProducer.sendClickEvent(shortCode);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(URI.create(originalUrl));

	    return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
	
	@GetMapping("/urls/{shortCode}/stats")
	public ResponseEntity<ShortUrlDto> getStats(@PathVariable String shortCode) {
	    return ResponseEntity.ok(service.getStats(shortCode));
	}
	
	@PatchMapping("/urls/{shortCode}/deactivate")
	public ResponseEntity<Void> deactivate(@PathVariable String shortCode) {

	    service.deactivateUrl(shortCode);

	    return ResponseEntity.noContent().build();
	}

}
