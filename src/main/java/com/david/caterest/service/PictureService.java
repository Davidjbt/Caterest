package com.david.caterest.service;

import com.david.caterest.dto.PicturePostDto;
import com.david.caterest.entity.Picture;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PictureService {
    Picture findPictureById(Long id);
    List<Picture> findAllPicturesByOrderByDateOfPostDesc();
    Picture savePicture(Picture picture);
    void saveImageFile(Picture picture, MultipartFile file);
    PicturePostDto findPostDetailsById(String id);
    void renderPicture(String id, HttpServletResponse response) throws IOException;
}
