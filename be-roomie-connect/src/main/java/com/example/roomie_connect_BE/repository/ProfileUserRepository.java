package com.example.roomie_connect_BE.repository;

import com.example.roomie_connect_BE.entity.ProfileUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUser, String> {
}
