package io.github.blueberry44477.service.user;

import io.github.blueberry44477.dto.request.CreateUserRequest;
import io.github.blueberry44477.dto.response.UserDto;

public interface UserService {
    public UserDto registerUser(CreateUserRequest request);
}
