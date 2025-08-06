package com.saad.wheelly.securityconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;


    public String generateJwtToken(String username, List<String> roles) {
        return JwtUtil.generateToken(username, roles, secretKey, jwtExpiration);
    }

    public boolean validateToken(String jwt) {

        return JwtUtil.validateToken(jwt, secretKey);

    }


    public String getUserNameFromJwtToken(String jwt) {

        return JwtUtil.getUsernameFromToken(jwt, secretKey);
    }
}
