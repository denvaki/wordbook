package com.likorn_devaki.wordbook.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Component
public class PasswordEncoder {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public static String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }
    public static boolean match(String encodedPassword, CharSequence palinTextPassword){
        return encoder.matches(palinTextPassword, encodedPassword);
    }
}