package com.example.kafkaJourny.repositories;

import com.example.kafkaJourny.entities.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEventCreatorRepository extends JpaRepository<UserMessages, UUID> {
}
