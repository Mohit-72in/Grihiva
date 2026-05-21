package com.grihakhata.controller;

import com.grihakhata.dto.JwtResponse;
import com.grihakhata.dto.LoginRequest;
import com.grihakhata.dto.RefreshTokenRequest;
import com.grihakhata.dto.TokenRefreshResponse;
import com.grihakhata.security.JwtTokenProvider;
import com.grihakhata.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.grihakhata.domain.RefreshToken;
import com.grihakhata.domain.User;

import com.grihakhata.service.RefreshTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "Authentication and token management")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(summary = "Login", description = "Authenticate with phone number and password to receive tokens.")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = tokenProvider.generateToken(principal);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(principal.getId());
        JwtResponse response = new JwtResponse(
                token,
                refreshToken.getToken(),
                principal.getId(),
                principal.getAuthorities().iterator().next().getAuthority()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Refresh access token", description = "Exchange a refresh token for a new access token.")
    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .map(token -> {
                    try {
                        return refreshTokenService.verifyExpiration(token);
                    } catch (IllegalStateException ex) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
                    }
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));

        User user = refreshToken.getUser();
        String accessToken = tokenProvider.generateToken(UserPrincipal.fromUser(user));
        return ResponseEntity.ok(new TokenRefreshResponse(accessToken));
    }
}
