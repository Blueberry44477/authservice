package io.github.blueberry44477.authservice.exception;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String token, String message) {
        super(message);
    }
}
