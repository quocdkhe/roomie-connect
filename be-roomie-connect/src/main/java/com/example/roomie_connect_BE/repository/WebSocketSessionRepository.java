package com.example.roomie_connect_BE.repository;


import com.example.roomie_connect_BE.entity.WebSocketSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebSocketSessionRepository extends JpaRepository<WebSocketSession, Long> {

    void deleteBySessionId(String socketSessionId);

}
