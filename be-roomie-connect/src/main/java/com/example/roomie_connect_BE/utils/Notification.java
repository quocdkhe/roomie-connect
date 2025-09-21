package com.example.roomie_connect_BE.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Notification {

    LOGIN_SUCCESS("Login successful", 200),
    LOGIN_FAILURE("Login failed", 401),
    REGISTER_SUCCESS("Registration successful", 200),
    INTROSPECT_SUCCESS("Introspection successful", 200),
    LOGOUT_SUCCESS("Logout successful", 200);
    private String message;
    private int code;
}
