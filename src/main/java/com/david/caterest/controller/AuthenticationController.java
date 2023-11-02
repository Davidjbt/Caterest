package com.david.caterest.controller;

import com.david.caterest.dto.AuthenticationResponse;
import com.david.caterest.dto.user.UserLogInDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestPart("user") UserSignUpDto user,
                                                         @RequestPart("inpFile") MultipartFile profilePicture) {
        return ResponseEntity.ok(authenticationService.register(user, profilePicture));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserLogInDto request,
                                                               HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.authenticate(request, response));
    }

    @PostMapping("/logOut")
    public ResponseEntity<AuthenticationResponse> logOut(HttpServletRequest request,
                                                         HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.logOut(request, response));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Exception> handleConflictError(ResponseStatusException exception) {
        // todo figure out ResponseStatusException
        return ResponseEntity.status(exception.getStatusCode()).body(exception);
    }

}
