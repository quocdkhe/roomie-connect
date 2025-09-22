package com.example.roomie_connect_BE.controller;


import com.example.roomie_connect_BE.dto.request.LoginRequest;
import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ApiResponse;
import com.example.roomie_connect_BE.dto.response.LoginResponse;
import com.example.roomie_connect_BE.service.AuthService;
import com.example.roomie_connect_BE.utils.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/identity")
public class AuthController {

    private final AuthService authService;

    @Value("${idp.client.id}")
    private String clientId;

    @Value("${idp.client.secret}")
    private String clientSecret;

    @Value("${idp.token.endpoint}")
    private String tokenEndpoint;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse lg = authService.loginKeyCloak(loginRequest, response);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + lg.getToken())
                .body(lg);

    }

    @PostMapping("/register")
    public ApiResponse<Object> register(@RequestBody ProfileUserRequest profileUserRequest) {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.REGISTER_SUCCESS.getMessage())
                .data(authService.register(profileUserRequest))
                .build();
    }



    @PostMapping("/logout/{userId}")
    public ApiResponse<Object> logout(HttpServletRequest request, @PathVariable("userId") String userId) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        authService.logout(userId);
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.LOGOUT_SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/change-pass")
    public ApiResponse<Object> changePasswordUser(@RequestBody ProfileUserRequest profileUserRequest) {
        return ApiResponse.builder()
                .code(1000)
                .message("")
                .data(authService.changePassword(profileUserRequest))
                .build();
    }
}
