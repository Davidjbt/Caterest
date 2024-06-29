package com.david.caterest.picture;

import com.david.caterest.picture.dto.PictureDetailsDto;
import com.david.caterest.picture.dto.PicturePostDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PictureService {
    List<Picture> findAllPicturesByOrderByDateOfPostDesc();
    PictureDetailsDto findPostDetailsById(String id);
    void renderPicture(String id, HttpServletResponse response) throws IOException;
    void savePicture(PicturePostDto picturePostDto, MultipartFile image, HttpServletRequest request);
    void saveImageFile(Picture picture, MultipartFile file);
}
