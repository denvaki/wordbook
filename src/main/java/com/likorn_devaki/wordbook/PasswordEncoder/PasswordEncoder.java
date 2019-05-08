package com.likorn_devaki.wordbook.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

//@Component
public class PasswordEncoder {

    public static String encode(CharSequence rawPassword) {

// TODO! Proper password encoding
// source https://dev.to/awwsmm/how-to-encrypt-a-password-in-ja..
        final char[] chars = rawPassword.toString().toCharArray();
        final byte[] bytes = "notSecuredSaltBecauseOfTime".getBytes();
        final int numOfIterations = 32768;
        final int keyLen = 256;
        final String encryptionMethod = "PBKDF2WithHmacSHA512";

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, numOfIterations, keyLen);

        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(encryptionMethod);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.err.println("Exception encountered in hashPassword()");
            return null;

        } finally {
            spec.clearPassword();
        }
    }

}
