package com.likorn_devaki.wordbook.Entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class Token {

    public Token(User user) {
        userId = user.getId();
        expirationTime = LocalDateTime.now().plus(2, ChronoUnit.HOURS);
    }

    private Integer userId;
    private LocalDateTime expirationTime;
}
