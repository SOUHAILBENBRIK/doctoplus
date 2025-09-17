package com.doctoplus.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Document("disponibilities")
@Schema(description = "Disponibility (availability) of a professional")
public class Disponibility {
    @Id
    @Schema(description = "Unique identifier of the disponibility", example = "64f29c9a3c2b9d4b567a12ef")
    private String id;

    @Schema(description = "Start time of availability", example = "2025-09-20T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "End time of availability", example = "2025-09-20T12:00:00")
    private LocalDateTime endTime;

    @DBRef
    @Schema(description = "Professional associated with this disponibility")
    private User pro;

    @Schema(description = "Available slots during the time range", example = "[\"09:00:00\", \"09:30:00\", \"10:00:00\"]")
    private List<LocalTime> slots;
}
