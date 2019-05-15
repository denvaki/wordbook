package com.likorn_devaki.wordbook.PasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

//@Component
public class PasswordEncoder2 {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public static String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }
    public static boolean match(String encodedPassword, CharSequence palinTextPassword){
        return encoder.matches(palinTextPassword, encodedPassword);
    }
}