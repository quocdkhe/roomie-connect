package com.example.roomie_connect_BE.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PostRequest {
    private String title;
    private String description;
    private Integer price;
    private Integer area;
    private String location;
    private Boolean isAvailable;
    private Boolean isLookingForRoommate;
}
