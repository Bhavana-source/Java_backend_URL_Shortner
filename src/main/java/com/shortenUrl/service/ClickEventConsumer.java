package com.shortenUrl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.shortenUrl.dto.ClickEvent;
import com.shortenUrl.entity.ShortUrlEntity;
import com.shortenUrl.repository.ShortUrlRepo;

@Service
public class ClickEventConsumer {

    @Autowired
    private ShortUrlRepo shortUrlRepo;

    @KafkaListener(topics = "click-events", groupId = "url-shortener-group")
    public void consume(ClickEvent event) {

        ShortUrlEntity entity = shortUrlRepo.findByShortCode(event.getShortCode())
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        entity.setClickCount(entity.getClickCount() + 1);

        shortUrlRepo.save(entity);
    }
}
