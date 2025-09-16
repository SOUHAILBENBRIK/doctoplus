package com.doctoplus.backend.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Document("disponibilities")
public class Disponibility {
    @Id
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @DBRef
    private User pro;

    private List<LocalTime> slots;
}
