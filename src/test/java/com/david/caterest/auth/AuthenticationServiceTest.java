package com.david.caterest.auth;

import com.david.caterest.config.JwtService;
import com.david.caterest.user.User;
import com.david.caterest.user.UserMapper;
import com.david.caterest.user.UserRepository;
import com.david.caterest.user.UserService;
import com.david.caterest.user.dto.UserLogInDto;
import com.david.caterest.user.dto.UserSignUpDto;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

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
        assertThatThrownBy(() ->
            authenticationService.register(dto, profilePicture, response)
        ).isInstanceOf(ResponseStatusException.class)
          .hasMessageContaining("Username already exists");
        assertThat(response.getCookies()).isEmpty();
        verify(userRepository, only()).existsByEmail(user.getEmail());
    }

    @Test
    public void shouldAuthenticateUser() {
        // Given
        String email = "test@gmail.com";
        UserLogInDto userDto = new UserLogInDto();
        userDto.setEmail(email);
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = User.builder().id(1L).displayName("John123").email(email).build();
        String jwt = "tokenTestValue";

        // Mock the calls
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), userDto.getPassword()
        ))).thenReturn(new UsernamePasswordAuthenticationToken(user, jwt));

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwt);

        // When
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(userDto, response);

        // Then
        assertThat(authenticationResponse.getUserId()).isEqualTo(user.getId());
        assertThat(authenticationResponse.getUsername()).isEqualTo(user.getDisplayName());
        assertThat(response.getCookies()).hasSize(2);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        // Given
        String email = "test@gmail.com";
        UserLogInDto userDto = new UserLogInDto();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mock the calls
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(
                () -> authenticationService.authenticate(userDto, response)
        ).isInstanceOf(NoSuchElementException.class);
        assertThat(response.getCookies()).isEmpty();
    }

    @Test
    public void shouldLogOutUser() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Cookie cookie = new Cookie("token", "tokenTestValue");
        request.setCookies(cookie);

        // When
        AuthenticationResponse authenticationResponse = authenticationService.logOut(request, response);

        // Then
        assertThat(authenticationResponse.getUserId()).isEqualTo(0);
        assertThat(authenticationResponse.getUsername()).isEmpty();
    }

    @Test
    public void shouldReturnNullCookieNotFound() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setCookies(new Cookie("noToken", "noTokenTestValue"));
        // When
        AuthenticationResponse authenticationResponse = authenticationService.logOut(request, response);

        // Then
        assertThat(authenticationResponse).isNull();
    }

}
