package com.david.caterest.controller;

import com.david.caterest.entity.Comment;
import com.david.caterest.entity.Picture;
import com.david.caterest.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    PictureService pictureService;

    public CommentController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/comment/{pictureId}")
    public String newComment(String commentText, @PathVariable String pictureId) {
        Comment comment = new Comment();

        Picture picture = pictureService.findPictureById(Long.valueOf(pictureId));

        comment.setCommentText(commentText);

        return "redirect:/picture/" + pictureId + "/show";
    }

}
