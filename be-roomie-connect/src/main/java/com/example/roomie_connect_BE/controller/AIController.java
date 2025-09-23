package com.example.roomie_connect_BE.controller;


import com.example.roomie_connect_BE.dto.response.ApiResponse;
import com.example.roomie_connect_BE.entity.ChatMessageAI;
import com.example.roomie_connect_BE.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AIController {
    private final AIService aiService;

    @PostMapping("/chat")
    ApiResponse<Object> chat(@RequestBody ChatMessageAI request) {
        return ApiResponse.builder()
                .code(1000)
                .message("send message success")
                .data(aiService.chat(request))
                .build();
    }

    @PostMapping("/chat-with-image")
    ApiResponse<Object> chatWithImage(@RequestParam("file")MultipartFile file,
                         @RequestParam("message") String message) throws IOException {
        return ApiResponse.builder()
                .code(1000)
                .message("send message image success")
                .data(aiService.chatWithImage(file,message))
                .build();
    }

    @PostMapping
    ApiResponse<Object> chatWithImageAndUserId(@RequestParam("file1")MultipartFile file1,
                         @RequestParam("file2") MultipartFile file2) throws IOException {
        return ApiResponse.builder()
                .code(1000)
                .message("verify image success")
                .data(aiService.verifyUserImage(file1,file2))
                .build();
    }


}