package io.github.blueberry44477.authservice.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.JwtCore;
import io.github.blueberry44477.authservice.dto.UserDetailsImpl;
import io.github.blueberry44477.authservice.dto.request.SigninRequest;
import io.github.blueberry44477.authservice.dto.request.SignupRequest;
import io.github.blueberry44477.authservice.exception.EmailAlreadyUsedException;
import io.github.blueberry44477.authservice.mapper.UserMapStruct;
import io.github.blueberry44477.authservice.model.User;
import io.github.blueberry44477.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapStruct userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    @Transactional(readOnly = true)
    public String signin(SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return jwtCore.generateToken(userDetails);
    }

    @Transactional
    public void signup(SignupRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyUsedException(request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}
