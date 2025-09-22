package com.example.roomie_connect_BE.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${minio.config.username}")
    private String minioUserName;

    @Value("${minio.config.password}")
    private String minioPassword;

    @Value("${minio.config.url}")
    private String url_access;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url_access)
                .credentials(minioUserName, minioPassword)
                .build();
    }

}
