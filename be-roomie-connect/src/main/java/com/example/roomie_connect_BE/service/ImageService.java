package com.example.roomie_connect_BE.service;


import com.example.roomie_connect_BE.dto.response.ImageResponse;
import com.example.roomie_connect_BE.dto.response.UrlImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageResponse postImageVerify(MultipartFile imageAvar, MultipartFile imageVerify) throws Exception;
    ImageResponse postImage(MultipartFile imageAvar) throws Exception;
}
