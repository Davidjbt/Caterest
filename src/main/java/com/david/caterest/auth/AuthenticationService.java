package com.david.caterest.auth;

import com.david.caterest.config.JwtService;
import com.david.caterest.user.User;
import com.david.caterest.user.UserMapper;
import com.david.caterest.user.UserRepository;
import com.david.caterest.user.UserService;
import com.david.caterest.user.dto.UserLogInDto;
import com.david.caterest.user.dto.UserSignUpDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static com.david.caterest.user.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthenticationResponse register(UserSignUpDto userDto,
                                           MultipartFile profilePicture,
                                           HttpServletResponse response) {

        User user = userMapper.toUser(userDto);
        userService.setUserProfilePicture(user, profilePicture);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(USER);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");

        userRepository.save(user);

        addAuthenticationCookies(response, user);

        return AuthenticationResponse.builder()
                .userId(user.getId())
                .username(user.getDisplayName())
                .build();
    }

    public AuthenticationResponse authenticate(UserLogInDto userDto,
                                               HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        ); // If this code is executed without errors it is good

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(); // todo: catch the exception and handle

        addAuthenticationCookies(response, user);

        return AuthenticationResponse.builder()
                .userId(user.getId())
                .username(user.getDisplayName())
                .build();
    }

    public AuthenticationResponse logOut(HttpServletRequest request,
                                         HttpServletResponse response) {
        Cookie token = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .orElse(null);

        if (token != null) {
            token.setValue(null);
            token.setMaxAge(0);
            token.setPath("/");
            response.addCookie(token);

            return AuthenticationResponse.builder()
                    .userId(0L)
                    .username("")
                    .build();
        }

        return null;
    }

    private void addAuthenticationCookies(HttpServletResponse response, User user) {
        String jwt = jwtService.generateToken(user);
        int cookieMaxAge = (int)(60 * 24);

        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(cookieMaxAge);
        response.addCookie(cookie);

        Cookie expirationCookie = new Cookie("token-expiration", String.valueOf(System.currentTimeMillis() + 1000 * (cookieMaxAge)));
        expirationCookie.setHttpOnly(false);
        expirationCookie.setDomain("localhost");
        expirationCookie.setPath("/");
        expirationCookie.setMaxAge(cookieMaxAge + 2 * 60);
        response.addCookie(expirationCookie);
    }

}
