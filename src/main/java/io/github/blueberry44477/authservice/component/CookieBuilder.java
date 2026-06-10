package io.github.blueberry44477.authservice.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieBuilder {
    @Value("${jwt.refresh-token-expiration}")
    private long refreshExpirationInSeconds;

    @Value("${jwt.refresh-token-path}")
    private String refreshTokenPath;

    public ResponseCookie refreshToken(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path(refreshTokenPath)
                .maxAge(refreshExpirationInSeconds)
                .build();
    }

    public ResponseCookie deleteRefreshToken() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path(refreshTokenPath)
                .maxAge(0)
                .build();
    }
}
