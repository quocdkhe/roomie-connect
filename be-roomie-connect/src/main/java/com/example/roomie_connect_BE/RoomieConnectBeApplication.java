package com.example.roomie_connect_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RoomieConnectBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomieConnectBeApplication.class, args);
	}

}
