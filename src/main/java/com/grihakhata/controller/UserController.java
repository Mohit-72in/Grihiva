package com.grihakhata.controller;

import com.grihakhata.domain.User;
import com.grihakhata.dto.UserRegistrationRequestDTO;
import com.grihakhata.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Renter onboarding")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Onboard renter", description = "Create a renter account with ROLE_RENTER and KYC pending.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/onboard-renter")
    public ResponseEntity<User> onboardRenter(@Valid @RequestBody UserRegistrationRequestDTO request) {
        return ResponseEntity.ok(userService.onboardRenter(request));
    }
}

