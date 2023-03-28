package com.david.caterest.controller;

import com.david.caterest.dto.UserDto;
import com.david.caterest.entity.Comment;
import com.david.caterest.entity.Picture;
import com.david.caterest.entity.User;
import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class CommentController {

    private final PictureService pictureService;
    private final UserService userService;

    public CommentController(PictureService pictureService, UserService userService) {
        this.pictureService = pictureService;
        this.userService = userService;
    }

    // Change function when implementing JWT
//    @PostMapping("/comment/{pictureId}")
//    public String newComment(@ModelAttribute("comment") Comment comment, @PathVariable String pictureId) {
//        String username = comment.getUser().getUsername(); // Will not be needed when JWT is implemented.
////        String password = comment.getUser().getPassword();
//
//        UserDto user = userService.findUserByUsername(username);
//
//        // todo implement better error template
//        if (user == null) return "/error";
//
//        System.out.println("'" + comment.getUser().getUsername() + "' '" + comment.getUser().getPassword() + "' from Comment Controller");
//        comment.setUser(user);
//        comment.setDateOfPost(LocalDateTime.now());
//
//        System.out.println(pictureId);
//
//        Picture picture = pictureService.findPictureById(Long.valueOf(pictureId));
//        comment.setPicture(picture);
//        picture.getComments().add(comment);
//        pictureService.savePicture(picture);
//
//        return "redirect:/picture/" + pictureId + "/show";
//    }

}
