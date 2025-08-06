package com.saad.wheelly.securityconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public static String generateToken(String username, List<String> roles, String secretKey, long jwtExpiration) {

        log.info("Generating token for user: {}, {} with user id {}", secretKey, jwtExpiration, username);

        return Jwts
                .builder()
                .setSubject(username)
                .addClaims(Map.of("roles", roles))
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, getSignInKey(secretKey))
                .compact();


    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static boolean validateToken(String token, String secretKey) {
        try {
            log.info("Validating token for user: {}", secretKey);
            Jwts.parserBuilder().setSigningKey(getSignInKey(secretKey)).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {

            log.error("Invalid JWT signature: {}", e.getMessage());
        }
        return false;
    }

    public static String getUsernameFromToken(String token, String secretKey) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }
    public static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secretKey) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token, String secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
