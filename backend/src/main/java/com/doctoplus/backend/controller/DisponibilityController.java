package com.doctoplus.backend.controller;

import com.doctoplus.backend.entity.Disponibility;
import com.doctoplus.backend.service.DisponibilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/disponibilities")
@Tag(name = "Disponibility", description = "Endpoints for managing disponibilities (availabilities)")
public class DisponibilityController {
    private final DisponibilityService disponibilityService;

    public DisponibilityController(DisponibilityService disponibilityService) {
        this.disponibilityService = disponibilityService;
    }

    @Operation(
            summary = "Get disponibilities by professional ID",
            description = "Retrieve available slots with dates for a given professional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of disponibilities retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Professional not found")
            }
    )
    @GetMapping("/pro/{proId}")
    public List<Map<String, String>> getDisponibilitiesByPro(@PathVariable String proId) {
        return disponibilityService.getAvailableSlotsWithDate(proId);
    }

    @Operation(
            summary = "Get disponibilities for the authenticated professional",
            description = "Retrieve all disponibilities of the currently logged-in professional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of disponibilities retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - no valid authentication found")
            }
    )
    @GetMapping("/pro/me")
    public List<Disponibility> getDisponibilitiesByPro() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return disponibilityService.getDisponibilitiesByMe(email);
    }

    @Operation(
            summary = "Get a disponibility by ID",
            description = "Retrieve a single disponibility record by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disponibility retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Disponibility not found")
            }
    )
    @GetMapping("/{id}")
    public Optional<Disponibility> getDisponibilityById(@PathVariable String id) {
        return disponibilityService.getDisponibilityById(id);
    }

    @Operation(
            summary = "Create a new disponibility",
            description = "Add a new disponibility for a professional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disponibility created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    @PostMapping
    public Disponibility createDisponibility(@RequestBody Disponibility disponibility) {
        return disponibilityService.saveDisponibility(disponibility);
    }

    @Operation(
            summary = "Delete a disponibility",
            description = "Delete a disponibility by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disponibility deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Disponibility not found")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteDisponibility(@PathVariable String id) {
        disponibilityService.deleteDisponibility(id);
    }
}
