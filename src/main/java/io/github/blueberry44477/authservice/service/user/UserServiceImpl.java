package io.github.blueberry44477.authservice.service.user;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.blueberry44477.authservice.dto.request.CreateUserRequest;
import io.github.blueberry44477.authservice.dto.response.UserDto;
import io.github.blueberry44477.authservice.mapper.UserMapStruct;
import io.github.blueberry44477.authservice.model.User;
import io.github.blueberry44477.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserMapStruct userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getUsers(Pageable pageable) {
        return repository.findAll(pageable).map(userMapper::toDto);
    }


    // @Override
    // @Transactional
    // public void addFriend(Long userId, Long friendId) {
    //     if (userId.equals(friendId)) {
    //         throw new IllegalArgumentException("You cannot add yourself to friends.");
    //     }

    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    //     User friend = userRepository.findById(friendId)
    //             .orElseThrow(() -> new ResourceNotFoundException("Friend not found with id: " + friendId));

    //     // Оскільки у вашій моделі @ManyToMany є двонаправленим логічно, але описана як однонаправлена,
    //     // додаємо друга поточній сутності. Зв'язок у таблиці 'user_friends' створиться автоматично.
    //     user.getFriends().add(friend);
    //     userRepository.save(user); 
    // }

    // @Override
    // @Transactional
    // public void removeFriend(Long userId, Long friendId) {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    //     User friend = userRepository.findById(friendId)
    //             .orElseThrow(() -> new ResourceNotFoundException("Friend not found with id: " + friendId));

    //     user.getFriends().remove(friend);
    //     userRepository.save(user);
    // }

    // @Override
    // public Set<UserResponse> getUserFriends(Long userId) {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
    //     // Мапимо Set<User> у Set<UserResponse> для безпечного відправлення на фронтенд
    //     return user.getFriends().stream()
    //             .map(userMapper::toResponse)
    //             .collect(Collectors.toSet());
    // }
}
