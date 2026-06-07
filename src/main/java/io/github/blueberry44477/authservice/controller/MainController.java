package io.github.blueberry44477.authservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.blueberry44477.authservice.dto.UserDto;
import io.github.blueberry44477.authservice.dto.request.FriendshipRequest;
import io.github.blueberry44477.authservice.service.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/secured")
public class MainController {
    private final UserService service;

    // @GetMapping("/user")
    // public String userAccess(Principal principal) {
    //     if (principal == null)
    //         return null;
    //     return principal.getName();
    // }

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

    @PostMapping("/friends/add")
    public ResponseEntity<Void> addFriend(
        Principal principal,
        @RequestBody FriendshipRequest request
    ) {
        service.addFriend(principal.getName(), request.getFriendEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/friends/remove")
    public ResponseEntity<Void> removeFriend(
        Principal principal,
        @RequestBody FriendshipRequest request
    ) {
        service.removeFriend(principal.getName(), request.getFriendEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/user/avatar",
        produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> getAvatar(
        Principal principal
    ) {
        return ResponseEntity.ok().body(service.getAvatar(principal.getName()));
    }
}
