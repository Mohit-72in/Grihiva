package com.grihakhata.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Refresh token request")
public class RefreshTokenRequest {
    @Schema(example = "0ff4f0bb-2a90-4f85-9f2d-4b61caa0e1a5", description = "Refresh token")
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
