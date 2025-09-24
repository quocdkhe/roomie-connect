package com.example.roomie_connect_BE.service;


import com.example.roomie_connect_BE.dto.request.LoginRequest;
import com.example.roomie_connect_BE.dto.request.UserRequest;
import com.example.roomie_connect_BE.dto.response.LoginResponse;
import com.example.roomie_connect_BE.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponse loginKeyCloak(LoginRequest loginRequest, HttpServletResponse response);

    UserResponse register(UserRequest profileUserRequest);

    UserResponse changePassword(UserRequest profileUserRequest);

    void logout(String userId);

//    LoginResponse loginOauth2(String code);
}
