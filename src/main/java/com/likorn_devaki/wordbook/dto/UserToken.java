package com.likorn_devaki.wordbook.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserToken {
    public String username;
    public String token;
}

