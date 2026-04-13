package com.shortenUrl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shortenUrl.entity.ShortUrlEntity;

@Repository
public interface ShortUrlRepo extends JpaRepository<ShortUrlEntity, Long>{
	Boolean existsByShortCode(String shortCode);
	Optional<ShortUrlEntity> findByOriginalUrl(String originalUrl);
	Optional<ShortUrlEntity> findByShortCode(String shortCode);
}
