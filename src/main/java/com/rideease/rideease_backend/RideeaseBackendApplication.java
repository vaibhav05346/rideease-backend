package com.rideease.rideease_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class RideeaseBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(RideeaseBackendApplication.class, args);
	}

}
