package com.example.kafkaJourny.services;

import com.example.kafkaJourny.dtos.UserDTO;
import com.example.kafkaJourny.dtos.UserMessagesRecord;
import com.example.kafkaJourny.entities.User;
import com.example.kafkaJourny.entities.UserMessages;
import com.example.kafkaJourny.events.producer.RabbitMQProducer;
import com.example.kafkaJourny.repositories.UserEventCreatorRepository;
import com.example.kafkaJourny.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserEventCreatorRepository userEventCreatorRepository;
    private final RabbitMQProducer rabbitMQProducer;

    public UserService(UserRepository userRepository, UserEventCreatorRepository userEventCreatorRepository, RabbitMQProducer rabbitMQProducer) {
        this.userRepository = userRepository;
        this.userEventCreatorRepository = userEventCreatorRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public List<UserDTO> getUsers() {
        List<UserDTO> usersDTOS = userRepository
                .findAll()
                .stream()
                .map(user -> new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId()))
                .collect(Collectors.toList());

        // Use forEach to perform side effects
        usersDTOS.stream().forEach(user -> {
            UserMessages userMessages = new UserMessages();
            userMessages.setId(user.getId());
            userMessages.setMessage("User " + user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
            try {
                rabbitMQProducer.sendMessage(userMessages);
                logger.info("[USER SERVICE] - Sent message for user {}", user.getId());
            } catch (JsonProcessingException e) {
                logger.error("[USER SERVICE] - Failed to send message for user {}", user.getId(), e);
                throw new RuntimeException(e);
            }
        });

        return usersDTOS;
    }

    public void save(UserMessagesRecord payload) {
        UserMessages userMessages = new UserMessages();
        userMessages.setMessage(payload.message());
        userMessages.setId(String.valueOf(payload.id()));
        userEventCreatorRepository.save(userMessages);
    }

    public User saveUser(UserDTO user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        try {
            UserMessages userMessages = new UserMessages();
            userMessages.setId(user.getId());
            logger.info("[USER SERVICE] - Sent message for user {}", newUser.getId());
            userMessages.setMessage("User " + newUser.getFirstName() + " " + newUser.getLastName() + " " + newUser.getEmail() + " created!");
            rabbitMQProducer.sendMessage(userMessages);
        } catch(JsonProcessingException e) {
            logger.info("[USER SERVICE] - Failed to send message for user {}", newUser.getId());
        }
        return userRepository.save(newUser);
    }
}
