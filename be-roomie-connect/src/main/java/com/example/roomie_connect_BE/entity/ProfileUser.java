package com.example.roomie_connect_BE.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String userId;
    private String provider;


}