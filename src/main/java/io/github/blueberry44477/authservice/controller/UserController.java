package io.github.blueberry44477.authservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.blueberry44477.authservice.dto.request.CreateUserRequest;
import io.github.blueberry44477.authservice.dto.request.UserUpdate;
import io.github.blueberry44477.authservice.dto.response.UserDto;
import io.github.blueberry44477.authservice.model.User;
import io.github.blueberry44477.authservice.service.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService service;

    @GetMapping
    public Page<UserDto> getUsers(Pageable pageable) {
        return service.getUsers(pageable);
    }

    // @PutMapping("/{id}")
    // @PreAuthorize("#id == authentication.principal.id") // Дозволено лише якщо id в URL збігається з id в токені
    // public UserResponse updateUser(@PathVariable Long id, @RequestBody UserUpdate request) {
    //     return userService.update(id, request);
    // }

    
    // @GetMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public UserResponse getUserById(@PathVariable Long id) {
    //     return userService.getUserById(id);
    // }

    // @PostMapping("/{id}/friends/{friendId}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
    //     userService.addFriend(id, friendId);
    // }

    // @DeleteMapping("/{id}/friends/{friendId}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
    //     userService.removeFriend(id, friendId);
    // }

    // @GetMapping("/{id}/friends")
    // @ResponseStatus(HttpStatus.OK)
    // public Set<UserResponse> getUserFriends(@PathVariable Long id) {
    //     return userService.getUserFriends(id);
    // }
}
