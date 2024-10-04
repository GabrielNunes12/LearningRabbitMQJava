package com.example.kafkaJourny.events.producer;

import com.example.kafkaJourny.entities.UserMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(UserMessages messageEntity) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(messageEntity);
        rabbitTemplate.convertAndSend("my_queue", message);
        System.out.println("Message sent: " + message);
    }
}
