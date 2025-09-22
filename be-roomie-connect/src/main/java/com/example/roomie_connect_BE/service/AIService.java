package com.example.roomie_connect_BE.service;

import com.example.roomie_connect_BE.entity.ChatMessageAI;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AIService {
    List<Object> chatWithImage(MultipartFile file, String message) throws IOException;

    String chat(ChatMessageAI request);

    boolean verifyUserImage(MultipartFile file1, MultipartFile file2);
}
