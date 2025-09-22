package com.example.roomie_connect_BE.controller;


import com.example.roomie_connect_BE.dto.response.ApiResponse;
import com.example.roomie_connect_BE.service.ImageService;
import com.example.roomie_connect_BE.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/img")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{userId}")
    public ApiResponse<Object> postImage(@RequestParam("img") MultipartFile imageFile, @PathVariable("userId") String userId) throws Exception {

        return ApiResponse.builder()
                .code(1000)
                .message(Notification.POST_IMAGE_SUCCESS.getMessage())
                .data(imageService.postImage(imageFile, userId))
                .build();
    }

    @GetMapping("/avar")
    public ApiResponse<Object> getImageUrl(@RequestParam("userId") String userId) {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.GET_IMAGE_SUCCESS.getMessage())
                .data(imageService.getImageUrl(userId))
                .build();
    }
}
