package com.grihakhata.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request payload")
public class LoginRequest {
    @Schema(example = "9999990001", description = "Registered phone number")
    @NotBlank
    private String phoneNumber;

    @Schema(example = "Admin@123", description = "Account password")
    @NotBlank
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
