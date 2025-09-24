package com.example.roomie_connect_BE.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;

import com.example.roomie_connect_BE.entity.WebSocketSession;
import com.example.roomie_connect_BE.repository.UserRepository;
import com.example.roomie_connect_BE.service.JWTService;
import com.example.roomie_connect_BE.service.WebSocketSessionService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Component
public class SocketHandler {
    private final SocketIOServer server;
    private final JWTService jwtService;
    private final WebSocketSessionService webSocketSessionService;
    private final UserRepository userRepository;

    @OnConnect
    public void clientConnected(SocketIOClient socketIOClient) {
        String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
        try {
            var userId = jwtService.getUserIdFromJWT(token);
            if (userId == null || StringUtils.isEmpty(userId)) {
                log.error("Socket Client {}: Kết nối thất bại, userId không hợp lệ, token: {}", socketIOClient.getSessionId(), token);
                socketIOClient.disconnect();
                return;
            }
            webSocketSessionService.create(WebSocketSession.builder()
                    .sessionId(socketIOClient.getSessionId().toString())
                    .user(userRepository.findById(userId).get())
                    .build());
            log.info("Socket Client {}: Kết nối thành công với userId: {}", socketIOClient.getSessionId(), userId);
        } catch (Exception e) {
            log.error("Socket Client {}: Lỗi xác thực token: {}", socketIOClient.getSessionId(), e.getMessage());
            socketIOClient.disconnect();
        }
    }



    @OnDisconnect
    public void clientDisconnected(SocketIOClient client) {
        log.info("Client disConnected: {}", client.getSessionId());
        webSocketSessionService.deleteBySocketSessionId(client.getSessionId().toString());
    }

    @PostConstruct
    public void startServer() {
        server.start();
        server.addListeners(this);
        log.info("Socket server started rồiiiiiiiii");
        log.info("Socket server started on port: {}", server.getConfiguration().getPort());
        log.info("Socket server context: {}", server.getConfiguration().getContext());
        log.info("Socket server hostname: {}", server.getConfiguration().getHostname());
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
        log.info("Socket server stoped");
    }
}