package com.example.infrastructure.authconfig;


import com.example.domain_models.exceptions.AppException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtVerifier {
    private final String secretKey;

    public JwtVerifier(@Value("${auth.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    public Claims verify(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        validateExpiry(claims);
        return claims;
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private void validateExpiry(Claims claims) {
        if (!claims.getExpiration().after(new Date())) {
            throw new AppException.UnAuthorized("JWT token expired");
        }
    }
}