package com.example.roomie_connect_BE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfiguration {
    @Value("${spring.security.oauth2.resource-server.jwt.jwk-set-uri}")
    private String jwtSetUri;

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter(){
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtAuthenticationConverter());
//        return jwtAuthenticationConverter;
//    }
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwtSetUri).build();
    }
}
