package com.example.roomie_connect_BE.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProfileUser {
    @Id
    private String id;

    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String provider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<WebSocketSession> sessions = new ArrayList<>();

}