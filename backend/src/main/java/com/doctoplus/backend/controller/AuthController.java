package com.doctoplus.backend.controller;

import com.doctoplus.backend.entity.User;
import com.doctoplus.backend.repository.UserRepository;
import com.doctoplus.backend.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user, encodes the password, and returns a JWT token with user details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(
                savedUser.getEmail(),
                Map.of("role", savedUser.getRole().name())
        );
        return Map.of(
                "token", token,
                "user", Map.of(
                        "id", savedUser.getId(),
                        "name", savedUser.getName(),
                        "email", savedUser.getEmail(),
                        "role", savedUser.getRole()
                )
        );
    }

    @Operation(
            summary = "Login a user",
            description = "Authenticates the user with email and password. Returns a JWT token with user details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            User user = userOpt.get();
            String token = jwtService.generateToken(user.getEmail(), Map.of("role", user.getRole().name()));
            return Map.of(
                    "token", token,
                    "user", Map.of(
                            "id", user.getId(),
                            "name", user.getName(),
                            "email", user.getEmail(),
                            "role", user.getRole()
                    )
            );
        }
        throw new RuntimeException("Invalid credentials");
    }

}
