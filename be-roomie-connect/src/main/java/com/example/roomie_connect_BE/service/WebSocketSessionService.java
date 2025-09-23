package com.example.roomie_connect_BE.service;


import com.example.roomie_connect_BE.entity.WebSocketSession;

public interface WebSocketSessionService {
    WebSocketSession create(WebSocketSession session);

    void deleteBySocketSessionId(String socketSessionId);
}
