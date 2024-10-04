package com.example.kafkaJourny.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user-messages")
public class UserMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String message;
    public UserMessages() {}
    public UserMessages(String message) {
        this.message = message;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
