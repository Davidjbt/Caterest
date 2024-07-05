package com.david.caterest.auth;

import com.david.caterest.config.JwtService;
import com.david.caterest.user.User;
import com.david.caterest.user.UserMapper;
import com.david.caterest.user.UserRepository;
import com.david.caterest.user.UserService;
import com.david.caterest.user.dto.UserSignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldRegisterUser() {
        // Given
        User user = new User();
        UserSignUpDto userDto = new UserSignUpDto();
        MockMultipartFile profilePicture = new MockMultipartFile("file", "test.jpg", "image/jpeg", "hola".getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();
        String encodedPassword = "encodedPassword";
        String jwt = "tokenTestValue";

        // Mock the calls
        when(userMapper.toUser(userDto)).thenReturn(user);
        doNothing().when(userService).setUserProfilePicture(user, profilePicture);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(jwt);

        // When
        AuthenticationResponse authenticationResponse = authenticationService.register(userDto, profilePicture, response);

        // Then
        assertThat(authenticationResponse).isNotNull();
        assertThat(authenticationResponse.getUsername()).isEqualTo(user.getEmail());
        assertThat(response.getCookies()).hasSize(2);
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        // Given
        User user = new User();
        UserSignUpDto dto = new UserSignUpDto();
        MockMultipartFile profilePicture = new MockMultipartFile("file", "test.jpg", "image/jpeg", "hola".getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mock the calls
        when(userMapper.toUser(dto)).thenReturn(user);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> {
            authenticationService.register(dto, profilePicture, response);
        }).isInstanceOf(ResponseStatusException.class)
          .hasMessageContaining("Username already exists");
        assertThat(response.getCookies()).isEmpty();
        verify(userRepository, only()).existsByEmail(user.getEmail());
    }

    @Test
    public void shouldAuthenticateUser() {
        // Given
        // Mock the calls
        // When
        // Then
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        // Given
        // Mock the calls
        // When
        // Then
    }

    @Test
    public void shouldLogOutUser() {
        // Given
        // Mock the calls
        // When
        // Then
    }

    @Test
    public void shouldReturnNullCookieNotFound() {
        // Given
        // Mock the calls
        // When
        // Then
    }

}