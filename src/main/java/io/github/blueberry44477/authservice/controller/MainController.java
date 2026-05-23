package io.github.blueberry44477.authservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.blueberry44477.authservice.dto.UserDto;
import io.github.blueberry44477.authservice.service.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/secured")
public class MainController {
    private final UserService service;

    @GetMapping("/user")
    public String userAccess(Principal principal) {
        if (principal == null)
            return null;
        return principal.getName();
    }

    @GetMapping("/users")
    public Page<UserDto> getUsers(
        @PageableDefault(size = 10, sort = "firstName")
        Pageable pageable) {
        return service.getUsers(pageable);
    }

    @GetMapping("/friends")
    public Page<UserDto> getFriends(
        Principal principal, 
        @PageableDefault(size = 10, sort = "firstName") Pageable pageable
    ) {
        return service.getFriendsByEmail(principal.getName(), pageable);
    }
}
