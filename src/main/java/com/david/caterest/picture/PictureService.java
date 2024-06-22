package com.david.caterest.picture;

import com.david.caterest.picture.dto.PictureDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PictureService {
    Picture findPictureById(Long id);
    List<Picture> findAllPicturesByOrderByDateOfPostDesc();
    Picture savePicture(Picture picture);
    void saveImageFile(Picture picture, MultipartFile file);
    PictureDetailsDto findPostDetailsById(String id);
    void renderPicture(String id, HttpServletResponse response) throws IOException;
    void postPicture(PictureDetailsDto picturePostDto, MultipartFile image, HttpServletRequest request);
}
