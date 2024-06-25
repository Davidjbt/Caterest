package com.david.caterest.picture;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findAllByOrderByDateOfPostDesc();
}
