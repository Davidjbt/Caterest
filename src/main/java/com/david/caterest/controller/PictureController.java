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
    @GetMapping("/user/{id}/image/{index}")
    public void renderImageFromDB(@PathVariable String id, @PathVariable String index, HttpServletResponse response) throws IOException {
        User user = userService.findUserById(Long.valueOf(id));
        Picture picture = user.getPictures().get(Integer.parseInt(index));

        if (!user.getPictures().isEmpty()) {
            byte[] byteArray = new byte[picture.getImage().length];
            int i = 0;

            for (Byte wrappedByte : picture.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
