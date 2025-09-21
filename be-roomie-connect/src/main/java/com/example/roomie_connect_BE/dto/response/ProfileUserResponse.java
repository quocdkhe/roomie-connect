package com.example.roomie_connect_BE.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileUserResponse {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String provider;

}
