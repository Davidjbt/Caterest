package com.david.caterest.repository;

import com.david.caterest.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    public List<Picture> findAllByOrderByDateOfPostDesc();
}
