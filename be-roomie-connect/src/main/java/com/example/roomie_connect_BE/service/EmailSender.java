package com.example.roomie_connect_BE.service;

public interface EmailSender {

    void sendEmailRegister(String to, String subject, String body);
}
