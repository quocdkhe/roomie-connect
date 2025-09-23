package com.example.roomie_connect_BE.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSocketSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String socketSessionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    ProfileUser user;

    Instant createdAt;
}
