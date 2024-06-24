package com.david.caterest.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void shouldFindAllUsersGivenUserRepositoryIsNotEmpty() {
        // given
        List<User> users = Collections.singletonList(user);

        // when
        when(userRepository.findAll()).thenReturn(users);

        // then
        List<User> result = userRepository.findAll();
        assertThat(result).isEqualTo(users);
    }

}
