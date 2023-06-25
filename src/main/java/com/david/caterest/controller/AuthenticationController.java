package com.david.caterest.controller;

import com.david.caterest.dto.AuthenticationResponse;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.service.AuthenticationService;
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
    public ResponseEntity<AuthenticationResponse> SignUp(@RequestPart("user") UserSignUpDto user,
                                                         @RequestPart("inpFile") MultipartFile profilePicture) {
        return ResponseEntity.ok(authenticationService.register(user, profilePicture));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Exception> handleConflictError(ResponseStatusException exception) {
        // todo figure out ResponseStatusException
        return ResponseEntity.status(exception.getStatusCode()).body(exception);
    }

}
