package com.shortenUrl.entity;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "short_urls")
@Getter
@Setter
public class ShortUrlEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	long id;
	
	@Column(name = "original_url")
	String originalUrl;
	
	@Column(name = "short_code")
	String shortCode;
	
	@Column(name = "created_at")
	LocalDateTime createdAt;
	
	@Column(name = "expires_at")
	LocalDateTime expiresAt;
	
	@Column(name = "click_count")
	long clickCount;
	
	@Column(name = "is_active")
	Boolean isActive;
}
