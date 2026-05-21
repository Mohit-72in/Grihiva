package com.grihakhata.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtResponse {
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", description = "Access token")
    private final String accessToken;

    @Schema(example = "0ff4f0bb-2a90-4f85-9f2d-4b61caa0e1a5", description = "Refresh token")
    private final String refreshToken;

    @Schema(example = "Bearer", description = "Token type")
    private final String tokenType = "Bearer";

    @Schema(example = "1", description = "User identifier")
    private final Long userId;

    @Schema(example = "ROLE_ADMIN", description = "Assigned role")
    private final String role;

    public JwtResponse(String accessToken, String refreshToken, Long userId, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
