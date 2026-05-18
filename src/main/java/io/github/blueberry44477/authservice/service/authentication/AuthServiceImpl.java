package io.github.blueberry44477.authservice.service.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.dto.request.CreateUserRequest;
import io.github.blueberry44477.authservice.dto.request.LoginRequest;
import io.github.blueberry44477.authservice.dto.response.UserDto;
import io.github.blueberry44477.authservice.exception.EmailAlreadyUsedException;
import io.github.blueberry44477.authservice.exception.UnauthorizedException;
import io.github.blueberry44477.authservice.mapper.UserMapStruct;
import io.github.blueberry44477.authservice.model.User;
import io.github.blueberry44477.authservice.repository.UserRepository;
import io.github.blueberry44477.authservice.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapStruct userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserDto login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Wrong email or password"));
        
        // if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
        //     throw new UnauthorizedException("Wrong email or password");
        
        String jwtToken = jwtService.generateToken(user);

        UserDto userDto = userMapper.toDto(user);
        userDto.setToken(jwtToken);

        return userDto;
    }

    @Override
    @Transactional
    public UserDto register(CreateUserRequest request) {
        if (repository.existsByEmail(request.getEmail()))
            throw new EmailAlreadyUsedException(request.getEmail());

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toDto(repository.save(user));
    }
}
