package com.david.caterest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDisplayNameContainsIgnoreCase(@NonNull String displayName);
    Optional<User> findByDisplayName(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
