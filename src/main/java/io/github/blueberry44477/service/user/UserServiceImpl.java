package io.github.blueberry44477.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.dto.request.CreateUserRequest;
import io.github.blueberry44477.dto.response.UserDto;
import io.github.blueberry44477.mapper.UserMapStruct;
import io.github.blueberry44477.model.User;
import io.github.blueberry44477.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserMapStruct userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto registerUser(CreateUserRequest request) {
        if (repository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException(
                "User with email " + request.getEmail() + " already exists.");

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toDto(repository.save(user));
    }
}
