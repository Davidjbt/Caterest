package com.david.caterest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @PostMapping("/comment")
    public String newComment(String comment) {



    }

}
