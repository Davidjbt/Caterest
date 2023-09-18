package com.david.caterest.service;

import com.david.caterest.dto.PicturePostDto;
import com.david.caterest.entity.Picture;
import com.david.caterest.mapper.PictureMapper;
import com.david.caterest.repository.PictureRepository;
import com.david.caterest.util.ImageRender;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

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

    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    @Transactional
    public void saveImageFile(Picture picture, MultipartFile file) {

        try {
            // No need to deal with null case as this was checked before.
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            picture.setImage(byteObjects);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }

    @Override
    public PicturePostDto findPostDetailsById(String id) {
        Optional<Picture> picture = pictureRepository.findById(Long.valueOf(id));

        if (picture.isEmpty()) return null; //todo handle better

        return pictureMapper.toPicturePostDto(picture.get());
    }

    @Override
    public void renderPicture(String id, HttpServletResponse response) throws IOException {
        Optional<Picture> picture = pictureRepository.findById(Long.valueOf(id));

        if (picture.isEmpty()) return;

        ImageRender.renderImage(response, picture.get().getImage());
    }

}
