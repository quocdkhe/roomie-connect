package com.example.roomie_connect_BE.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Notification {

    LOGIN_SUCCESS("Login successful", 200),
    LOGIN_FAILURE("Login failed", 401),
    REGISTER_SUCCESS("Registration successful", 200),
    LOGOUT_SUCCESS("Logout successful", 200),
    GET_IMAGE_SUCCESS("Get Image Avarta Success", 200),
    CREATE_PROFILE_SUCCESS("Create profile success", 200),
    UPDATE_PROFILE_SUCCESS("Update profile success", 200),
    GET_PROFILE_SUCCESS("Get profile success", 200),
    GET_PROFILE_FAIL("Get profile fail", 400),
    GET_ALL_PROFILE_SUCCESS("Get all profiles success", 200),
    POST_IMAGE_SUCCESS("Upload Image Avarta Success", 200);
    private String message;
    private int code;
}
