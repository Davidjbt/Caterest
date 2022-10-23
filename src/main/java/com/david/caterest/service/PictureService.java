package com.david.caterest.service;

import com.david.caterest.entity.Picture;

import java.util.List;

public interface PictureService {
    Picture findPictureById(Long id);
    List<Picture> findAllPicturesByOrderByDateOfPostDesc();
}
