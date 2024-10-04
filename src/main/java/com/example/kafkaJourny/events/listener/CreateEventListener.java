package com.example.kafkaJourny.events.listener;

import com.example.kafkaJourny.dtos.UserMessagesRecord;
import com.example.kafkaJourny.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CreateEventListener {
    private final Logger logger = LoggerFactory.getLogger(CreateEventListener.class);

    private final UserService userService;

    public CreateEventListener (UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "user_list")
    public void listen(Message<UserMessagesRecord> message){
        logger.info("Message consumed: {}", message);

        userService.save(message.getPayload());
    }

}
