package com.david.caterest.picture;

import com.david.caterest.config.JwtService;
import com.david.caterest.picture.dto.PictureDetailsDto;
import com.david.caterest.picture.dto.PicturePostDto;
import com.david.caterest.user.User;
import com.david.caterest.user.UserRepository;
import com.david.caterest.util.ImageRender;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final PictureMapper pictureMapper;
    private final JwtService jwtService;


    public List<Long> findAllPictureIdsOrderedByDateDesc() {
        return pictureRepository.findAllByOrderByDateOfPostDesc().stream()
                .map(p -> p.getId())
                .toList();
    }

    @Override
    public PictureDetailsDto findPictureDetailsById(String id) {
        Optional<Picture> picture = pictureRepository.findById(Long.valueOf(id));

        if (picture.isEmpty()) return null; //todo handle better

        return pictureMapper.toPicturePostDto(picture.get());
    }

    @Override
    public void renderPicture(String id, HttpServletResponse response) throws IOException {
        Optional<Picture> picture = pictureRepository.findById(Long.valueOf(id));

        if (picture.isEmpty()) throw new IOException("Picture not found");

        ImageRender.renderImage(response, picture.get().getImage());
    }

    @Override
    public void savePicture(PicturePostDto picturePostDto, MultipartFile image, HttpServletRequest request) {
        Cookie jwtCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .orElse(null);

        if (jwtCookie != null) { // todo return response body
            Optional<User> userOptional = userRepository.findByEmail(jwtService.extractUsername(jwtCookie.getValue()));
            if (userOptional.isPresent()) { //todo handle better
                User user = userOptional.get();
                Picture picture = pictureMapper.toPicture(picturePostDto);

                picture.setDateOfPost(LocalDateTime.now());
//                picture.setComments(new ArrayList<>()); // it may be initialized by Hibernate
                saveImageFile(picture, image);

                user.getPictures().add(picture);
                picture.setUser(user);

                pictureRepository.save(picture);
                userRepository.save(user);
            }
        }
    }

    @Transactional
    protected void saveImageFile(Picture picture, MultipartFile file) {
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

}
