package io.github.blueberry44477.authservice.service.authentication;

import io.github.blueberry44477.authservice.dto.request.CreateUserRequest;
import io.github.blueberry44477.authservice.dto.request.LoginRequest;
import io.github.blueberry44477.authservice.dto.response.UserDto;

public interface AuthService {
    UserDto login(LoginRequest request);
    UserDto register(CreateUserRequest request);
}
