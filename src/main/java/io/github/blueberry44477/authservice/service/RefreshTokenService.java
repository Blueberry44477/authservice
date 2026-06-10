package io.github.blueberry44477.authservice.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.dto.AccessTokenDTO;
import io.github.blueberry44477.authservice.dto.TokensDTO;
import io.github.blueberry44477.authservice.exception.TokenRefreshException;
import io.github.blueberry44477.authservice.model.RefreshToken;
import io.github.blueberry44477.authservice.repository.RefreshTokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final JwtCore jwtCore;

    @Getter
    @Value("${jwt.refresh-token-expiration}")
    private Long expiration;

    @Transactional
    public RefreshToken createRefreshToken(String email) {
        repository.deleteByEmail(email);

        RefreshToken token = new RefreshToken();
        token.setEmail(email)
             .setToken(UUID.randomUUID().toString())
             .setExpiryDate(Instant.now().plusSeconds(expiration));

        return repository.save(token);
    }


    @Transactional
    public TokensDTO refreshTokens(String refreshToken) {
        RefreshToken oldToken = repository.findByToken(refreshToken)
                                          .map(this::verifyExpiration)
                                          .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database."));
                        
        String email = oldToken.getEmail();
        repository.delete(oldToken);

        RefreshToken newRefreshToken = createRefreshToken(email);

        AccessTokenDTO accessToken = new AccessTokenDTO(jwtCore.generateToken(email),
                                                        jwtCore.getTokenType(),
                                                        jwtCore.getLifetimeInSeconds());

        return new TokensDTO(accessToken, newRefreshToken.getToken());
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(java.time.Instant.now())) {
            repository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please sign in again.");
        }
        return token;
    }
}
