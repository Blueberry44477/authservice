package io.github.blueberry44477.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.blueberry44477.authservice.dto.request.SigninRequest;
import io.github.blueberry44477.authservice.dto.request.SignupRequest;
import io.github.blueberry44477.authservice.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest request) {
        return ResponseEntity.ok(service.signin(request));
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