package com.example.roomie_connect_BE.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private String content;
    private String timestamp;
    private Long chatRoomId;
    private String userId;
}
