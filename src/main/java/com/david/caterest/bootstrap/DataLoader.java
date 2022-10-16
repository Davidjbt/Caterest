package com.david.caterest.bootstrap;

import com.david.caterest.entity.Picture;
import com.david.caterest.entity.User;
import com.david.caterest.repository.PictureRepository;
import com.david.caterest.repository.UserRepository;
import com.david.caterest.service.ImageGetter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    //https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization
    public DataLoader(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User david = new User();
        userRepository.save(david);

        david.setUserName("DavidJ");

        Picture testPicture1 = new Picture();
        Picture testPicture2 = new Picture();
        Picture testPicture3 = new Picture();

        pictureRepository.save(testPicture1);
        pictureRepository.save(testPicture2);
        pictureRepository.save(testPicture3);

        testPicture1.setDescription("Test picture 1");
        testPicture1.setDateOfPost(LocalDate.now());
        Byte[] testImage1 = ImageGetter.getImage("src/main/resources/static/images/test1.jpg");
        testPicture1.setImage(testImage1);
        david.getPictures().add(testPicture1);
        testPicture1.setUser(david);

        testPicture2.setDescription("Test picture 2");
        testPicture2.setDateOfPost(LocalDate.now());
        Byte[] testImage2 = ImageGetter.getImage("src/main/resources/static/images/test2.jpg");
        testPicture2.setImage(testImage2);
        david.getPictures().add(testPicture2);
        testPicture2.setUser(david);

        testPicture3.setDescription("Test picture 3");
        testPicture3.setDateOfPost(LocalDate.now());
        Byte[] testImage3 = ImageGetter.getImage("src/main/resources/static/images/test3.jpg");
        testPicture3.setImage(testImage3);
        david.getPictures().add(testPicture3);
        testPicture3.setUser(david);

        userRepository.save(david);

        pictureRepository.save(testPicture1);
        pictureRepository.save(testPicture2);
        pictureRepository.save(testPicture3);
    }
}

