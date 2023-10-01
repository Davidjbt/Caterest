package com.david.caterest.controller;

import com.david.caterest.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final PictureService pictureService;

    @GetMapping("/pictures")
    public List<Long> getPicturesIdToRenderOnHome() {
        List<Long> ids = new ArrayList<>();

        pictureService.findAllPicturesByOrderByDateOfPostDesc().forEach(
                picture -> ids.add(picture.getId())
        );

        return ids;
    }
}
