package com.example.roomie_connect_BE.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MatchRequestRequest {
    private Long postId;
    private String status;
}
