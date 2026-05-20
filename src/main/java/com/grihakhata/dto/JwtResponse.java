package com.grihakhata.dto;

public class JwtResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
    private final Long userId;
    private final String role;

    public JwtResponse(String accessToken, Long userId, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
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
