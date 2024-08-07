package com.david.caterest.home;

import com.david.caterest.picture.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final PictureService pictureService;

    @GetMapping("/pictures")
    public List<Long> getPicturesIdToRenderOnHome() {
        return pictureService.findAllPictureIdsOrderedByDateDesc();
    }
}
