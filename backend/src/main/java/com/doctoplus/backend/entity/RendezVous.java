package com.doctoplus.backend.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("rendezvous")
public class RendezVous {
    @Id
    private String id;
    private String userId; // Patient
    @DBRef
    private User pro;
    private LocalDateTime dateTime;
    private String status; // RESERVED / CANCELED
}
