package com.doctoplus.backend.controller;

import com.doctoplus.backend.entity.RendezVous;
import com.doctoplus.backend.service.RendezVousService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rendezvous")
@Tag(name = "RendezVous", description = "Endpoints for managing appointments (RendezVous)")
public class RendezVousController {
    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @Operation(
            summary = "Get rendezvous by user ID",
            description = "Retrieve all rendezvous associated with a specific user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendezvous list retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/user/{userId}")
    public List<RendezVous> getRendezVousByUser(@PathVariable String userId) {
        return rendezVousService.getByUser(userId);
    }

    @Operation(
            summary = "Get rendezvous by professional ID",
            description = "Retrieve all rendezvous associated with a specific professional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendezvous list retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Professional not found")
            }
    )
    @GetMapping("/pro/{proId}")
    public List<RendezVous> getRendezVousByPro(@PathVariable String proId) {
        return rendezVousService.getByPro(proId);
    }

    @Operation(
            summary = "Get rendezvous for authenticated professional",
            description = "Retrieve all rendezvous for the currently logged-in professional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendezvous list retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - no valid authentication found")
            }
    )
    @GetMapping("/pro/me")
    public List<RendezVous> getRendezVousByMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return rendezVousService.getByProMe(email);
    }

    @Operation(
            summary = "Get rendezvous by ID",
            description = "Retrieve details of a specific rendezvous by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendezvous retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Rendezvous not found")
            }
    )
    @GetMapping("/{id}")
    public Optional<RendezVous> getRendezVousById(@PathVariable String id) {
        return rendezVousService.getById(id);
    }

    @Operation(
            summary = "Create a new rendezvous",
            description = "Create a new appointment (RendezVous) between a user and a professional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendezvous created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    @PostMapping
    public RendezVous createRendezVous(@RequestBody RendezVous rendezVous) {
        return rendezVousService.saveRendezVous(rendezVous);
    }

    @Operation(
            summary = "Rate a professional for a rendezvous",
            description = "Allows a user to rate a professional after a rendezvous.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating submitted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid rating value"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - no valid authentication found"),
                    @ApiResponse(responseCode = "404", description = "Rendezvous not found")
            }
    )
    @PostMapping("/{rendezVousId}/rate")
    public void ratePro(
            @PathVariable String rendezVousId,
            @RequestParam int rating
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        rendezVousService.ratePro(rendezVousId, rating, email);
    }

    @Operation(
            summary = "Delete a rendezvous",
            description = "Delete a rendezvous by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rendezvous deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Rendezvous not found")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteRendezVous(@PathVariable String id) {
        rendezVousService.deleteRendezVous(id);
    }
}
