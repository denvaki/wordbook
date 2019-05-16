package com.likorn_devaki.wordbook;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWT {
    private String SECRET_KEY = "DF1C1A3940DAD2DA1455A6ACCBEAEDC2BE82B56EA3BC6A4DCF05FDF584FC35C0SHA256";
    public String createJWT(String username) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, "HS384");

        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        /*Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();*/

        //Let's set the JWT Claims
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}