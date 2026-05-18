package io.github.blueberry44477.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.blueberry44477.authservice.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"friends"})
    Page<User> findAll(Pageable pageable);
    
    @EntityGraph(attributePaths = {"friends"})
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);

    @Query("SELECT f FROM User u JOIN u.friends f WHERE u.email = :email")
    Page<User> findFriendsByEmail(@Param("email") String email, Pageable pageable);
}
