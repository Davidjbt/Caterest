package com.david.caterest.auth;

import com.david.caterest.user.dto.UserLogInDto;
import com.david.caterest.user.dto.UserSignUpDto;
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
                                                           @RequestPart("inpFile") MultipartFile profilePicture,
                                                           HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(user, profilePicture, response));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserLogInDto userDto,
                                                               HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.authenticate(userDto, response));
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
