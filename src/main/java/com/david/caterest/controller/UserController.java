package com.david.caterest.controller;

import com.david.caterest.dto.user.UserLogInDto;
import com.david.caterest.dto.user.UserProfileDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.User;
import com.david.caterest.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return null;
    }

    @GetMapping("/{userId}/profilePicture")
    public void renderUserProfilePicture(@PathVariable String userId, HttpServletResponse response) throws IOException {
        userService.renderProfilePicture(userId, response);
    }

    @GetMapping("/find")
    public List<UserProfileDto> findMatchingUsers(@RequestParam String query) {
        return userService.findMatchingUsers(query);
    }


    @GetMapping("/new")
    public UserSignUpDto newUser() {
        return new UserSignUpDto();
    }

    @GetMapping("/list")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleConflictError(ResponseStatusException exception) {
        // todo figure out ResponseStatusException
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @PostMapping("/testing")
    public ResponseEntity<?> testing(@ModelAttribute UserLogInDto user) {
        System.out.println(user.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/profile/{displayName}")
    public UserProfileDto getUserProfileDetails(@PathVariable String displayName) {
        return userService.findUserProfileDetailsByDisplayName(displayName);
    }

}