package com.example.roomie_connect_BE.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginResponse {
    boolean isValid;
    String token;
    String userId;
}
