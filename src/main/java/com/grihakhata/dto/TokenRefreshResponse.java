package com.grihakhata.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokenRefreshResponse {
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", description = "New access token")
    private final String accessToken;

    @Schema(example = "Bearer", description = "Token type")
    private final String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
