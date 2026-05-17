package io.github.blueberry44477.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.blueberry44477.authservice.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
