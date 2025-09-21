package com.example.roomie_connect_BE.service.serviceImpl;

import com.example.roomie_connect_BE.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public String getUserIdFromJWT(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();
            if (userId == null) {
                throw new IllegalArgumentException("UserId is null");
            }
            return userId;
        } catch (Exception e) {
            log.error("Cannot get userId from JWT: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}