package com.doctoplus.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Role of the user")
public enum Role {
    @Schema(description = "Patient role")
    PATIENT,

    @Schema(description = "Professional role")
    PRO
}
