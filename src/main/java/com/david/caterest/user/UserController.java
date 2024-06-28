package com.david.caterest.user;

import com.david.caterest.user.dto.UserProfileDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        return userService.findUsersByMatchingDisplayName(query);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleConflictError(ResponseStatusException exception) {
        // todo figure out ResponseStatusException
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @GetMapping("/profile/{displayName}")
    public UserProfileDto getUserProfileDetails(@PathVariable String displayName) {
        return userService.findUserProfileDetailsByDisplayName(displayName);
    }

}
