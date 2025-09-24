package com.example.roomie_connect_BE.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String provider;
    private boolean isVerified;

}
