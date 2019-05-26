package com.likorn_devaki.wordbook.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

public class JWTProvider {
    private static SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JWTProvider() {}

    public static String createJWT(Integer userId) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(String.valueOf(userId))
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean invalidToken(HttpServletRequest request) {
        return extractToken(request) == null;
    }

    public static Integer extractUserId(HttpServletRequest request) {
        try {
            return getUserId(extractToken(request));
        } catch (Exception ignored) {
            return null;
        }
    }

    private static Integer getUserId(String token) {
        return Integer.parseInt(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    private static Optional<String> resolveToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

    private static boolean invalidToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        try {
            return claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    private static String extractToken(HttpServletRequest request) {
        String token = JWTProvider.resolveToken(request).orElse(null);
        if (JWTProvider.invalidToken(token))
            return null;
        return token;
    }
}