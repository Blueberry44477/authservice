package io.github.blueberry44477.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.blueberry44477.authservice.dto.UserDetailsImpl;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return UserDetailsImpl.build(user);
    }

    // @Transactional(readOnly = true)
    // public Page<UserDto> getUsers(Pageable pageable) {
    //     return repository.findAll(pageable).map(userMapper::toDto);
    // }
    // public Set<UserResponse> getUserFriends(Long userId) {
    //     User user = repository.findById(userId)
    //             .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
    //     // Мапимо Set<User> у Set<UserResponse> для безпечного відправлення на фронтенд
    //     return user.getFriends().stream()
    //             .map(userMapper::toResponse)
    //             .collect(Collectors.toSet());
    // }

    // @Transactional
    // public void addFriend(Long userId, Long friendId) {
    //     if (userId.equals(friendId)) {
    //         throw new IllegalArgumentException("You cannot add yourself to friends.");
    //     }

    //     User user = repository.findById(userId)
    //             .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    //     User friend = repository.findById(friendId)
    //             .orElseThrow(() -> new EntityNotFoundException("Friend not found with id: " + friendId));

    //     user.getFriends().add(friend);
    //     repository.save(user); 
    // }

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
