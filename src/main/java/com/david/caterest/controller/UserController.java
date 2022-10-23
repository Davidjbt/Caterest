package com.david.caterest.controller;

import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final PictureService pictureService;

    public UserController(UserService userService, PictureService pictureService) {
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @GetMapping("/user/{id}")
    public String getUsers(Model model, @PathVariable String id) {
        model.addAttribute("user", userService.findUserById(Long.valueOf(id)));

        return "users/user_profile";
    }

    @GetMapping({"/", "/home", "/home.html"})
    public String getHome(Model model) {
        List<Long> ids = new ArrayList<>();

        pictureService.findAllPicturesByOrderByDateOfPostDesc().forEach(picture -> ids.add(picture.getId()));
        model.addAttribute("indices", ids);

        return "home";
    }
}
