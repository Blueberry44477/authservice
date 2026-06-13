package io.github.blueberry44477.authservice.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.blueberry44477.authservice.component.CookieBuilder;
import io.github.blueberry44477.authservice.dto.AccessTokenDTO;
import io.github.blueberry44477.authservice.dto.TokensDTO;
import io.github.blueberry44477.authservice.dto.request.SignInRequest;
import io.github.blueberry44477.authservice.dto.request.SignUpRequest;
import io.github.blueberry44477.authservice.model.RefreshToken;
import io.github.blueberry44477.authservice.service.AuthService;
import io.github.blueberry44477.authservice.service.RefreshTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;
    private final RefreshTokenService refreshTokenService;
    private final CookieBuilder cookieBuilder;

    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenDTO> signIn(@Valid @RequestBody SignInRequest request) {
        AccessTokenDTO response = service.signIn(request);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        ResponseCookie cookie = cookieBuilder.refreshToken(refreshToken.getToken());

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDTO> refreshTokens(
        @CookieValue String refreshToken
    ) {
        TokensDTO response = refreshTokenService.refreshTokens(refreshToken);
        ResponseCookie cookie = cookieBuilder.refreshToken(response.getRefreshToken());

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(response.getAccessToken());
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(
        @CookieValue(required = false) String refreshToken
    ) {
        if (refreshToken != null) {
            service.revokeUserSession(refreshToken);
        }
        ResponseCookie deleteCookie = cookieBuilder.deleteRefreshToken();
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                             .build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(
        @Valid @RequestBody SignUpRequest request
    ) {
        service.signUp(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> signUpBatch(
        @Valid @RequestBody List<SignUpRequest> requests
    ) {
        service.signUpBatch(requests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}