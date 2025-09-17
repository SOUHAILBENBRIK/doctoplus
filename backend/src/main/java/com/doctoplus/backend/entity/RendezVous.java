package com.doctoplus.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("rendezvous")
@Schema(description = "Appointment (RendezVous) between a patient and a professional")
public class RendezVous {
    @Id
    @Schema(description = "Unique identifier of the rendezvous", example = "64f29c9a3c2b9d4b567a12ef")
    private String id;

    @Schema(description = "ID of the patient who booked the rendezvous", example = "64f2b1cd4e6aaf1234567890")
    private String userId;

    @DBRef
    @Schema(description = "Professional associated with the rendezvous")
    private User pro;

    @Schema(description = "Date and time of the rendezvous", example = "2025-09-22T15:30:00")
    private LocalDateTime dateTime;

    @Schema(description = "Status of the rendezvous", example = "RESERVED")
    private String status; // RESERVED / CANCELED
}
