package io.github.blueberry44477.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.blueberry44477.authservice.component.CookieBuilder;
import io.github.blueberry44477.authservice.dto.SignInResponse;
import io.github.blueberry44477.authservice.dto.request.SigninRequest;
import io.github.blueberry44477.authservice.dto.request.SignupRequest;
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

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@Valid @RequestBody SigninRequest request) {
        SignInResponse response = service.signin(request);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        ResponseCookie cookie = cookieBuilder.refreshToken(refreshToken.getToken());

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
        @Valid @RequestBody SignupRequest request
    ) {
        service.signup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> signupBatch(
        @Valid @RequestBody List<SignupRequest> requests
    ) {
        service.signupBatch(requests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}