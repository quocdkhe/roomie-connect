package com.example.roomie_connect_BE.service.serviceImpl;


import com.example.roomie_connect_BE.dto.*;
import com.example.roomie_connect_BE.dto.request.LoginRequest;
import com.example.roomie_connect_BE.dto.request.UserRequest;
import com.example.roomie_connect_BE.dto.response.LoginResponse;
import com.example.roomie_connect_BE.dto.response.UserResponse;
import com.example.roomie_connect_BE.feignclient.IdentityClient;
import com.example.roomie_connect_BE.mapper.UserMapper;
import com.example.roomie_connect_BE.repository.UserRepository;
import com.example.roomie_connect_BE.service.AuthService;
import com.example.roomie_connect_BE.service.JWTService;
import com.example.roomie_connect_BE.utils.Utilities;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JWTService jwtService;
    private final IdentityClient identityClient;
    private final JwtDecoder decoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final Utilities utilities;

    @Value("${idp.client.id}")
    @NonFinal
    private String clientId;

    @Value("${idp.client.secret}")
    @NonFinal
    private String clientSecret;


    @Override
    public LoginResponse loginKeyCloak(LoginRequest loginRequest, HttpServletResponse response) {
        var user = identityClient.getUserToken(getTokenClient(), TokenExchangeParamUser.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("password")
                .username(loginRequest.getUserName())
                .password(loginRequest.getPassword())
                .scope("openid")
                .build());

        return LoginResponse.builder()
                .token(user.getAccessToken())
                .userId(jwtService.getUserIdFromJWT(user.getAccessToken()))
                .isValid(true)
                .build();
    }


    @Override
    public UserResponse register(UserRequest profileUserRequest) {

        log.info("Token is : {}", getTokenClient());
        var creationResponse = identityClient.createUser(getTokenClient(), UserCreationParam.builder()
                .username(profileUserRequest.getUserName())
                .firstName(profileUserRequest.getUserName())
                .lastName(profileUserRequest.getUserName())
                .email(profileUserRequest.getEmail())
                .enabled(true)
                .emailVerified(true)
                .credentials(List.of(Credential.builder()
                        .type("password")
                        .temporary(false)
                        .value(profileUserRequest.getPassword())
                        .build()))
                .build());
        String userId = extractUserId(creationResponse);
        log.info("UserId {} ", userId);
        var user = userMapper.toProfileUser(profileUserRequest);
        user.setId(userId);
        user.setProvider("LOCAL");
        user.setAvatar("Empty Avatar");
        return userMapper.toProfileUserResponse(userRepository.save(user));
    }


    @Override
    public void logout(String userId) {
        identityClient.logoutKeyloak(getTokenClient(), userId);
    }


    @Override
    public UserResponse changePassword(UserRequest profileUserRequest) {
        var id = utilities.getUserId();
        var user = identityClient.changePassword(getTokenClient(), Credential.builder()
                .temporary(false)
                .type("password")
                .value(profileUserRequest.getPassword())
                .build(), id);
        return UserResponse.builder()
                .id(id)
                .build();
    }

    private String extractUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().get("Location").getFirst();
        String[] splitStr = location.split("/");
        return splitStr[splitStr.length - 1];

    }

    private String getTokenClient() {
        var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                .grant_type("client_credentials")
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope("openid")
                .build());
        return "Bearer " + token.getAccessToken();
    }
}
