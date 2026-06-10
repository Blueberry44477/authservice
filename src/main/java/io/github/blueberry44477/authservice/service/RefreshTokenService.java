package io.github.blueberry44477.authservice.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.model.RefreshToken;
import io.github.blueberry44477.authservice.repository.RefreshTokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repository;

    @Getter
    @Value("${jwt.refresh-token-expiration}")
    private Long expiration;

    @Transactional
    public RefreshToken createRefreshToken(String email) {
        repository.deleteByEmail(email);

        RefreshToken token = new RefreshToken();
        token.setEmail(email)
             .setToken(UUID.randomUUID().toString())
             .setExpiryDate(Instant.now().plusMillis(expiration));

        return repository.save(token);
    }


    // @Transactional
    // public TokenRefreshResponse refreshAccessToken(String requestToken) {
    //     return refreshTokenRepository.findByToken(requestToken)
    //             .map(this::verifyExpiration)
    //             .map(RefreshToken::getEmail) // Using email instead of username per schema choices
    //             .map(email -> {
    //                 String accessToken = jwtCore.generateTokenFromUsername(email);
    //                 return new TokenRefreshResponse(accessToken, requestToken);
    //             })
    //             .orElseThrow(() -> new TokenRefreshException(requestToken, "Refresh token is not in database."));
    // }

    // private RefreshToken verifyExpiration(RefreshToken token) {
    //     if (token.getExpiryDate().isBefore(java.time.Instant.now())) {
    //         refreshTokenRepository.delete(token);
    //         throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please sign in again.");
    //     }
    //     return token;
    // }
}
