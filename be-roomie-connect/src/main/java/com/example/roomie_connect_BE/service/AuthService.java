package com.example.roomie_connect_BE.service;


import com.example.roomie_connect_BE.dto.request.LoginRequest;
import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.LoginResponse;
import com.example.roomie_connect_BE.dto.response.ProfileUserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponse loginKeyCloak(LoginRequest loginRequest, HttpServletResponse response);

    ProfileUserResponse register(ProfileUserRequest profileUserRequest);


    ProfileUserResponse changePassword(ProfileUserRequest profileUserRequest);

    void logout(String userId);

//    LoginResponse loginOauth2(String code);
}
