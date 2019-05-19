package com.likorn_devaki.wordbook.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

public class JWTProvider {
    private static SecretKey secretKey = null;

    private JWTProvider() {}

    private static void init() {
        if (secretKey == null)
            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static String createJWT(Integer userId) {
        init();
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(String.valueOf(userId))
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Integer getUserId(String token) {
        init();
        return Integer.parseInt(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    public static Optional<String> resolveToken(HttpServletRequest req) {
        init();
        return Optional.ofNullable(req.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

    public static boolean invalidToken(String token) {
        init();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        try {
            return claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }
}