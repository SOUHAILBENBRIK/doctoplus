package com.doctoplus.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
@Schema(description = "User entity representing a patient or professional")
public class User {
    @Id
    @Schema(description = "Unique identifier of the user", example = "64f29c9a3c2b9d4b567a12ef")
    private String id;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Hashed password of the user (not exposed in APIs)", example = "$2a$10$abc123hashed")
    private String password;

    @Schema(description = "Role of the user (PATIENT or PRO)", example = "PRO")
    private Role role;

    // Champs spécifiques PRO
    @Schema(description = "Specialty of the professional (null if patient)", example = "Cardiologist")
    private String specialty;

    @Schema(description = "Score of the professional (null if patient) from 0 to 100", example = "60")
    private Integer score;
}
