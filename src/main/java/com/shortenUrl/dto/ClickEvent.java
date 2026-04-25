package com.shortenUrl.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor   // 🔥 REQUIRED
@AllArgsConstructor
public class ClickEvent {

    private String shortCode;
    private LocalDateTime timestamp;

}
