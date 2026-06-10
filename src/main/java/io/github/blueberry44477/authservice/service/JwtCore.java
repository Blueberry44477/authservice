package io.github.blueberry44477.authservice.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.blueberry44477.authservice.dto.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Service
public class JwtCore {
    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.expiration}")
    private Long lifetime;

    @Getter
    @Value("${jwt.token-type}")
    private String tokenType;

    public String generateToken(UserDetailsImpl userDetails) {
        return generateToken(userDetails.getUsername());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                   .subject(username)
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + lifetime))
                   .signWith(getSigningKey(), Jwts.SIG.HS256)
                   .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    public Long getLifetimeInSeconds() {
        return lifetime / 1000;
    }

    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    //     final Claims claims = extractAllClaims(token);
    //     return claimsResolver.apply(claims);
    // }

    // public String generateToken(UserDetails userDetails) {
    //     return Jwts.builder()
    //             .subject(userDetails.getUsername())
    //             .issuedAt(new Date(System.currentTimeMillis()))
    //             .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
    //             .signWith(getSignInKey())
    //             .compact();
    // }

    // public boolean isTokenValid(String token, UserDetails userDetails) {
    //     final String username = extractUsername(token);
    //     return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    // }

    // private boolean isTokenExpired(String token) {
    //     return extractExpiration(token).before(new Date());
    // }

    // private Date extractExpiration(String token) {
    //     return extractClaim(token, Claims::getExpiration);
    // }

    // private Claims extractAllClaims(String token) {
    //     return Jwts.parser()
    //             .verifyWith(getSignInKey())
    //             .build()
    //             .parseSignedClaims(token)
    //             .getPayload();
    // }
}
