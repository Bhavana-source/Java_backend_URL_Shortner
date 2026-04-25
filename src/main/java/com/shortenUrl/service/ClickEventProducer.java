package com.shortenUrl.service;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.shortenUrl.dto.ClickEvent;

@Service
public class ClickEventProducer {

    private final KafkaTemplate<String, ClickEvent> kafkaTemplate;

    public ClickEventProducer(KafkaTemplate<String, ClickEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClickEvent(String shortCode) {
        ClickEvent event = new ClickEvent(shortCode, LocalDateTime.now());
        kafkaTemplate.send("click-events", event);
    }
}
