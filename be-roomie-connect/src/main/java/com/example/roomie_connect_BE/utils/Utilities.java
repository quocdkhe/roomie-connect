package com.example.roomie_connect_BE.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class Utilities {
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((JwtAuthenticationToken) authentication).getToken().getSubject();
        return userId;
    }

}