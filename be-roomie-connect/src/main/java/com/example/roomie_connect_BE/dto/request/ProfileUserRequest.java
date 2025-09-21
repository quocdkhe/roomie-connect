package com.example.roomie_connect_BE.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileUserRequest {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String userId;
    private String avatar;
    private String provider;

}
