package com.david.caterest.controller;

import com.david.caterest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String getUsers(Model model, @PathVariable String id) {
        model.addAttribute("user", userService.findUserById(Long.valueOf(id)));

        return "users/user_profile";
    }


}
