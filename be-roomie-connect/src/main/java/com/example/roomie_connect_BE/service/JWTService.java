package com.example.roomie_connect_BE.service;


public interface JWTService {

    Boolean validateToken(String token);

    String getUserIdFromJWT(String token);


//    String refreshToken(String token, Users users);
}
