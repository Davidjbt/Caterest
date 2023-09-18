package com.david.caterest.controller;

import com.david.caterest.dto.PicturePostDto;
import com.david.caterest.entity.Comment;
import com.david.caterest.entity.Picture;
import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PictureController {

    private final UserService userService;
    private final PictureService pictureService;

    @GetMapping("/picture/{pictureId}")
    public void renderPictureByPictureId(@PathVariable String pictureId, HttpServletResponse response) throws IOException {
        pictureService.renderPicture(pictureId, response);
    }

    @GetMapping("/picture/{pictureId}/post")
    public PicturePostDto post(@PathVariable String pictureId) {
        return pictureService.findPostDetailsById(pictureId);
    }

    @GetMapping("/picture/{pictureId}/show")
    public String showById(@PathVariable String pictureId, Model model) {
        model.addAttribute("picture", pictureService.findPictureById(Long.valueOf(pictureId)));
        model.addAttribute("comment", new Comment());

        return "picture/show";
    }

    @GetMapping("/picture/new")
    private String newPicture(Model model) {
        model.addAttribute("picture", new Picture());

        return "picture/picture-form";
    }

    // todo change function when implementing JWT
//    @PostMapping("/picture")
//    public String save(@ModelAttribute("picture") Picture picture, @RequestParam("inpFile") MultipartFile file, BindingResult bindingResult) {
//        String username = picture.getUser().getUsername(); // Will get replaced when implementing JWT
////        String password = picture.getUser().getPassword();
//
//        UserDto user = userService.findUserByUsername(username);
//
//        // todo implement better error template
//        if (user == null) return "/error";
//
////        System.out.println("'" + picture.getUser().getUsername() + "' '" + picture.getUser().getPassword() + "'");
//
//        pictureService.saveImageFile(picture, file);
//        picture.setDateOfPost(LocalDateTime.now());
//
//        user.getPictures().add(picture);
//        picture.setUser(user);
//
//        pictureService.savePicture(picture);
//        userService.saveUser(user);
//        Picture savedPicture = user.getPictures().get(user.getPictures().indexOf(picture));
//
//        return "redirect:/picture/" + savedPicture.getId() + "/show";
//    }
}
