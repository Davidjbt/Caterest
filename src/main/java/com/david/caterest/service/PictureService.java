package com.david.caterest.service;

import com.david.caterest.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {
    Picture findPictureById(Long id);
    List<Picture> findAllPicturesByOrderByDateOfPostDesc();
    Picture savePicture(Picture picture);
    void saveImageFile(Picture picture, MultipartFile file);
}
