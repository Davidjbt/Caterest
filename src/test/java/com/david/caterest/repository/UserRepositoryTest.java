package com.david.caterest.repository;

import com.david.caterest.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindStudentWhenGivenExistentDisplayName() {
        // given
        String displayName = "test123";
        User user = new User();
        user.setDisplayName(displayName);
        userRepository.save(user);

        // when
        Optional<User> result = userRepository.findByDisplayName(displayName);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    void shouldFindStudentWhenGivenNonexistentDisplayName() {
        // given
        String email = "test123";

        // when
        Optional<User> result = userRepository.findByDisplayName(email);

        // then
        assertThat(result).isNotPresent();
    }

    @Test
    void shouldFindStudentWhenGivenExistentEmail() {
        // given
        String email = "test@gmail.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        // when
        Optional<User> result = userRepository.findByEmail(email);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    void shouldNotFindStudentWhenGivenNonexistentEmail() {
        // given
        String email = "test@gmail.com";

        // when
        Optional<User> result = userRepository.findByEmail(email);

        // then
        assertThat(result).isNotPresent();
    }

    @Test
    void shouldReturnTrueWhenGivenExistentEmail() {
        // given
        String email = "test@gmail.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        // when
        boolean result = userRepository.existsByEmail(email);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenGivenNonexistentEmail() {
        // given
        String email = "test@gmail.com";

        // when
        boolean result = userRepository.existsByEmail(email);

        // then
        assertThat(result).isFalse();
    }

}