package com.david.caterest.controller;

import com.david.caterest.dto.user.UserLogInDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.User;
import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final PictureService pictureService;

    public UserController(UserService userService, PictureService pictureService) {
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return null;
    }

    @GetMapping({"/", "/home"})
    public String getHome(Model model) {
        List<Long> ids = new ArrayList<>();

        // This list will have the pictures ids in a reverse chronological order.
        pictureService.findAllPicturesByOrderByDateOfPostDesc().forEach(picture -> ids.add(picture.getId()));
        model.addAttribute("indices", ids);

        return "home";
    }

    @GetMapping("/home/pictures")
    public List<Long> getPicturesIdToRenderOnHome() {
        List<Long> ids = new ArrayList<>();

        pictureService.findAllPicturesByOrderByDateOfPostDesc().forEach(
                picture -> ids.add(picture.getId())
        );

        return ids;
    }

    @GetMapping("/user/new")
    public UserSignUpDto newUser() {
        return new UserSignUpDto();
    }

    @GetMapping("/users/list")
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
}