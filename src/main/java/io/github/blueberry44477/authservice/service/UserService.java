package io.github.blueberry44477.authservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.dto.UserDetailsImpl;
import io.github.blueberry44477.authservice.dto.UserDto;
import io.github.blueberry44477.authservice.exception.EntityNotFoundException;
import io.github.blueberry44477.authservice.mapper.UserMapStruct;
import io.github.blueberry44477.authservice.model.User;
import io.github.blueberry44477.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapStruct userMapper;

    @Transactional(readOnly = true)
    public Page<UserDto> getUsers(Pageable pageable) {
        return repository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return UserDetailsImpl.build(user);
    }

    public Page<UserDto> getFriendsByEmail(String email, Pageable pageable) {
        Page<User> friends = repository.findFriendsByEmail(email, pageable);
        return friends.map(userMapper::toDto);
    }

    @Transactional
    public void addFriend(String userEmail, String friendEmail) {
        if (userEmail.equals(friendEmail)) {
            throw new IllegalArgumentException("You cannot add yourself to friends.");
        }

        User user = repository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));
        User friend = repository.findByEmail(friendEmail)
            .orElseThrow(() -> new EntityNotFoundException("Friend not found with email: " + friendEmail));

        user.getFriends().add(friend);
        repository.save(user); 
    }

    //TODO
    // @Transactional
    // public void removeFriend(Long userId, Long friendId) {
    //     User user = repository.findById(userId)
    //             .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    //     User friend = repository.findById(friendId)
    //             .orElseThrow(() -> new EntityNotFoundException("Friend not found with id: " + friendId));

    //     user.getFriends().remove(friend);
    //     repository.save(user);
    // }
}
