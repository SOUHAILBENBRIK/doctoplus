package com.doctoplus.backend.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password; // hashed
    private Role role;

    // Champs spécifiques PRO
    private String specialty; // null si patient
    private Integer score;    // null si patient
}