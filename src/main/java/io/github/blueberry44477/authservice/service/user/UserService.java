package io.github.blueberry44477.authservice.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.blueberry44477.authservice.dto.request.CreateUserRequest;
import io.github.blueberry44477.authservice.dto.response.UserDto;

public interface UserService {
    Page<UserDto> getUsers(Pageable pageable);
}
