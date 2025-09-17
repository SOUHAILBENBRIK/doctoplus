package com.doctoplus.backend.controller;

import com.doctoplus.backend.entity.User;
import com.doctoplus.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users (patients & professionals)")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all patients",
            description = "Retrieve a list of all users with the role `PATIENT`.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Patients retrieved successfully")
            }
    )
    @GetMapping("/patients")
    public List<User> getAllPatients() {
        return userService.getAllPatients();
    }

    @Operation(
            summary = "Get all professionals",
            description = "Retrieve a list of all users with the role `PRO` (professional).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Professionals retrieved successfully")
            }
    )
    @GetMapping("/pros")
    public List<User> getAllPros() {
        return userService.getAllPros();
    }

    @Operation(
            summary = "Get user by ID",
            description = "Retrieve a specific user by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Delete a user",
            description = "Delete a user from the system by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
