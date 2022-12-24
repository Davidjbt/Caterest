package com.david.caterest.controller;

import com.david.caterest.entity.User;
import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

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
    public String getUser(Model model, @PathVariable String id) {
        model.addAttribute("user", userService.findUserById(Long.valueOf(id)));

        return "user/user-profile";
    }

    @GetMapping({"/", "/home", "/home.html"})
    public String getHome(Model model) {
        List<Long> ids = new ArrayList<>();

        // This list will have the pictures ids in a reverse chronological order.
        pictureService.findAllPicturesByOrderByDateOfPostDesc().forEach(picture -> ids.add(picture.getId()));
        model.addAttribute("indices", ids);

        return "home";
    }

    @GetMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());

        return "user/user-form";
    }

    @PostMapping("/user")
    public String save(@ModelAttribute("user") User user, @RequestParam("inpFile") MultipartFile file, BindingResult bindingResult) {
        System.out.println("Hi there 1");

        if (userService.findUserByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        System.out.println("Hi there 2");

        if (userService.findUserByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already registered");
        }


        userService.saveImageFile(user, file);
        User savedUser = userService.saveUser(user);

        return "redirect:/user/" + savedUser.getId();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleConflictError(Exception exception) {
        // todo figure out ResponseStatusException

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/400");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}