package com.example.roomie_connect_BE.repository;

import com.example.roomie_connect_BE.entity.MatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRequestRepository extends JpaRepository<MatchRequest, Long> {
}
