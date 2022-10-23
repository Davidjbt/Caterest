package com.david.caterest.controller;

import com.david.caterest.entity.Picture;
import com.david.caterest.entity.User;
import com.david.caterest.service.PictureService;
import com.david.caterest.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    @GetMapping("/user/{userId}/image/{index}")
    public void renderUserPictureFromDB(@PathVariable String userId, @PathVariable String index, HttpServletResponse response) throws IOException {
        User user = userService.findUserById(Long.valueOf(userId));
        Picture picture = user.getPictures().get(Integer.parseInt(index));
        Byte[] image = picture.getImage();

        renderImage(response, image);
    }

    @GetMapping("/user/{userId}/profilePicture")
    public void renderUserProfilePictureFromDB(@PathVariable String userId, HttpServletResponse response) throws IOException {
        User user = userService.findUserById(Long.valueOf(userId));
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
}
