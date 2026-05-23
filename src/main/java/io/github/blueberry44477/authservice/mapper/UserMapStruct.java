package io.github.blueberry44477.authservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import io.github.blueberry44477.authservice.dto.UserDto;
import io.github.blueberry44477.authservice.dto.request.SignupRequest;
import io.github.blueberry44477.authservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    UserDto toDto(User entity);
    User toEntity(SignupRequest dto);
    List<User> toEntityList(List<SignupRequest> dtos);
}
