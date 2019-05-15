package com.likorn_devaki.wordbook;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWT {
    private static String SECRET_KEY = "DF1C1A3940DAD2DA1455A6ACCBEAEDC2BE82B56EA3BC6A4DCF05FDF584FC35C0SHA256";
    public static String createJWT(String id, String username) {

        //The JWT signature algorithm we will be using to sign the token

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, "HS256");

        //Let's set the JWT Claims
        return Jwts.builder().setId(id)
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();

    }
    public static Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
