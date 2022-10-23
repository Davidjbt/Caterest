package com.david.caterest.service;

import com.david.caterest.entity.Picture;
import com.david.caterest.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture findPictureById(Long id) {
        Optional<Picture> picture = pictureRepository.findById(id);

        //todo implement 404 page to display error.
        if (picture.isEmpty()) return null;

        return picture.get();
    }

    public List<Picture> findAllPicturesByOrderByDateOfPostDesc() {
        return pictureRepository.findAllByOrderByDateOfPostDesc();
    }
}
