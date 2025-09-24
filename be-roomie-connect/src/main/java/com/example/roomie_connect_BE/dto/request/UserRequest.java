package com.example.roomie_connect_BE.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;

}
