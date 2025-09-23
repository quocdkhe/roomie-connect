package com.example.roomie_connect_BE.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SocketIOConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(8099);
        configuration.setContext("/msg");
        configuration.setOrigin("*");
        log.info("HostName is: {}", configuration.getHostname());
        configuration.setHostname("0.0.0.0");
        log.info("HostName Now is: {}", configuration.getHostname());
        return new SocketIOServer(configuration);

    }
}