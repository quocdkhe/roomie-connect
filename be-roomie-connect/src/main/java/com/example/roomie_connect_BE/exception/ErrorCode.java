package com.example.roomie_connect_BE.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    STUDENT_NOT_FOUND("Student not found", 404),
    PASSWORD_MISMATCH("Password mismatch", 401),
    LOGIN_FAIL("Login failed", 401);

    private String message;
    private int code;
}
