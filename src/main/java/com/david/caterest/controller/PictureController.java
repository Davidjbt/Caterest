package com.david.caterest.controller;

import com.david.caterest.dto.UserDto;
import com.david.caterest.entity.Comment;
import com.david.caterest.entity.Picture;
import com.david.caterest.entity.User;
import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Controller
public class PictureController {

    private final UserService userService;
    private final PictureService pictureService;

    public PictureController(UserService userService, PictureService pictureService) {
        this.userService = userService;
        this.pictureService = pictureService;
    }

    //todo to render multiples images, using a th:foreach might do the trick, adding a image id from the view.
    //todo read through documentation to understand this method.
    @GetMapping("/picture/{pictureId}")
    public void renderUserPictureFromDB(@PathVariable String pictureId, HttpServletResponse response) throws IOException {
        Picture picture = pictureService.findPictureById(Long.valueOf(pictureId));
        Byte[] image = picture.getImage();

        renderImage(response, image);
    }

    @GetMapping("/user/{userId}/profilePicture")
    public void renderUserProfilePictureFromDB(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.findUserById(Long.valueOf(userId));
        Byte[] image = user.getProfilePicture();

        renderImage(response, image);
    }

    @GetMapping("/home/{pictureId}")
    public void renderUserPictureToHomeFromDB(@PathVariable String pictureId, HttpServletResponse response) throws IOException {
        Picture picture = pictureService.findPictureById(Long.valueOf(pictureId));
        Byte[] image = picture.getImage();

        renderImage(response, image);
    }

    private void renderImage(HttpServletResponse response, Byte[] image) throws IOException {
        if (image.length != 0) {
            byte[] byteArray = new byte[image.length];
            int i = 0;

            for (Byte wrappedByte : image){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
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
