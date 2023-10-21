package com.david.caterest.service;

import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.Role;
import com.david.caterest.entity.User;
import com.david.caterest.mapper.UserMapper;
import com.david.caterest.repository.UserRepository;
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

    @Test
    void shouldAddUser() {
        // given
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        user = User.builder()
                .id(1L)
                .displayName("TestUser")
                .email("test123@gmail.com")
                .password("1234")
                .role(Role.USER)
                .build();

        // when
        when(userMapper.toUser(userSignUpDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // then
        final User result = userService.addUser(userSignUpDto);
        assertThat(result).isEqualTo(user);
    }
}