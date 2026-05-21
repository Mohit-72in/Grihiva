package com.grihakhata.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Renter onboarding request")
public class UserRegistrationRequestDTO {
    @Schema(example = "Amit Kumar", description = "Full name of the renter")
    @NotBlank
    private String fullName;

    @Schema(example = "9999991234", description = "Phone number of the renter")
    @NotBlank
    private String phoneNumber;

    @Schema(example = "amit.kumar@example.com", description = "Email address of the renter")
    @Email
    private String email;

    @Schema(example = "Renter@123", description = "Initial password for the renter")
    @NotBlank
    @Size(min = 6)
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

