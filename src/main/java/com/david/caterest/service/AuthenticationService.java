package com.david.caterest.service;

import com.david.caterest.config.JwtService;
import com.david.caterest.dto.AuthenticationResponse;
import com.david.caterest.dto.user.UserLogInDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.User;
import com.david.caterest.mapper.UserMapper;
import com.david.caterest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.david.caterest.entity.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthenticationResponse register(UserSignUpDto userDto, MultipartFile profilePicture) {

        User user = userMapper.toUser(userDto);
        userService.setUserProfilePicture(user, profilePicture);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(USER);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(UserLogInDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        ); // If this code is executed without errors it is good

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(); // todo: catch the exception and handle

        String jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
