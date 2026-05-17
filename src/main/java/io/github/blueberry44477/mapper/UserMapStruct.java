package io.github.blueberry44477.mapper;

import org.mapstruct.Mapper;

import io.github.blueberry44477.dto.request.CreateUserRequest;
import io.github.blueberry44477.dto.response.UserDto;
import io.github.blueberry44477.model.User;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    UserDto toDto(User entity);
    User toEntity(CreateUserRequest dto);
}
