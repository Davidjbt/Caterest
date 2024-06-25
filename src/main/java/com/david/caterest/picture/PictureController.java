package com.david.caterest.picture;

import com.david.caterest.picture.dto.PictureDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping(("/picture"))
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/render/{pictureId}")
    public void renderPictureByPictureId(@PathVariable String pictureId,
                                         HttpServletResponse response) throws IOException {
        pictureService.renderPicture(pictureId, response);
    }

    @GetMapping("/post/{pictureId}")
    public PictureDetailsDto post(@PathVariable String pictureId) {
        return pictureService.findPostDetailsById(pictureId);
    }

    @PostMapping("/post")
    public void postPicture(@RequestPart("pictureDetails") PictureDetailsDto picturePostDto,
                            @RequestPart("inpFile") MultipartFile image,
                            HttpServletRequest request) {
        pictureService.savePicture(picturePostDto, image, request);
    }

}
