package com.example.roomie_connect_BE.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageAI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String message;

    String roomUserId;

    String imageUrl;

    String imageMimeType;

    boolean isFromUser;

    @LastModifiedDate
    LocalDateTime createdDate;
}