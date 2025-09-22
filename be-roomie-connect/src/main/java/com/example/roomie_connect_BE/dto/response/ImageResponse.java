package com.example.roomie_connect_BE.dto.response;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class ImageResponse {

    private String imgName;

    private String buckName;

    @CurrentTimestamp
    private LocalDateTime createAt;
}
