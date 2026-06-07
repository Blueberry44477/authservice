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
            .orElseThrow(() -> new EntityNotFoundException("User", userEmail));
        User friend = repository.findByEmail(friendEmail)
            .orElseThrow(() -> new EntityNotFoundException("User", friendEmail));

        user.getFriends().add(friend);
        repository.save(user); 
    }

    @Transactional
    public void removeFriend(String userEmail, String friendEmail) {
        User user = repository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User", userEmail));
        User friend = repository.findByEmail(friendEmail)
                .orElseThrow(() -> new EntityNotFoundException("User", friendEmail));

        user.getFriends().remove(friend);
        repository.save(user);
    }

    public byte[] getAvatar(String email) {
        User user = repository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User", email));
        
        if (user.getAvatar() == null) {
            throw new EntityNotFoundException("User with email: " + email + "does not have an avatar");
        }

        return user.getAvatar();
    }
}
