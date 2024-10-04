package com.example.kafkaJourny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaHandler;

@SpringBootApplication
public class KafkaJournyApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaJournyApplication.class, args);
	}

}
