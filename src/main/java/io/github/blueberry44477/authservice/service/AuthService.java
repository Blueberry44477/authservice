package io.github.blueberry44477.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.dto.AccessTokenDTO;
import io.github.blueberry44477.authservice.dto.UserDetailsImpl;
import io.github.blueberry44477.authservice.dto.request.SignInRequest;
import io.github.blueberry44477.authservice.dto.request.SignUpRequest;
import io.github.blueberry44477.authservice.exception.EmailAlreadyUsedException;
import io.github.blueberry44477.authservice.mapper.UserMapStruct;
import io.github.blueberry44477.authservice.model.User;
import io.github.blueberry44477.authservice.repository.RefreshTokenRepository;
import io.github.blueberry44477.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapStruct userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    @Transactional(readOnly = true)
    public AccessTokenDTO signIn(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // log.info("User {} successfully signed in", request.getEmail());
        return new AccessTokenDTO(jwtCore.generateToken(userDetails), 
                                  jwtCore.getTokenType(),
                                  jwtCore.getLifetimeInSeconds());
    }

    @Transactional
    public void signUp(SignUpRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyUsedException(request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    @Transactional
    public void revokeUserSession(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Transactional
    public void signUpBatch(List<SignUpRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        List<String> emailsToCheck = requests.stream()
                                             .map(SignUpRequest::getEmail)
                                             .toList();

        Set<String> existingEmails = repository.findExistingEmails(emailsToCheck);

        List<User> usersToSave = new ArrayList<>();

        for (SignUpRequest request : requests) {
            if (existingEmails.contains(request.getEmail())) {
                throw new EmailAlreadyUsedException(request.getEmail());
            }

            User user = userMapper.toEntity(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            
            usersToSave.add(user);
        }

        repository.saveAll(usersToSave);
    }
}
