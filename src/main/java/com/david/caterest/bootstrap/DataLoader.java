package com.david.caterest.bootstrap;

import com.david.caterest.picture.Picture;
import com.david.caterest.picture.PictureRepository;
import com.david.caterest.user.Role;
import com.david.caterest.user.User;
import com.david.caterest.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PasswordEncoder passwordEncoder;
    //https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization
    public DataLoader(UserRepository userRepository, PictureRepository pictureRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User david = new User();
        userRepository.save(david);


        david.setDisplayName("DavidJ");
        david.setEmail("test123@gmail.com");
        david.setPassword(passwordEncoder.encode("1234"));
        david.setBiography("Albert Einstein was born at Ulm, in Württemberg, Germany, on March 14, 1879. Six weeks later the family moved to Munich, where he later on began his schooling at the Luitpold Gymnasium.");

        david.setProfilePicture(ImageGetter.getImage("src/main/resources/static/images/Albert_Einstein_Head.jpg"));
        david.setRole(Role.USER);

        Picture testPicture1 = new Picture();
        Picture testPicture2 = new Picture();
        Picture testPicture3 = new Picture();

        pictureRepository.save(testPicture1);
        pictureRepository.save(testPicture2);
        pictureRepository.save(testPicture3);

        testPicture1.setDescription("Test picture 1");
        testPicture1.setDateOfPost(LocalDateTime.of(2022, 10, 21, 14, 1));
        Byte[] testImage1 = ImageGetter.getImage("src/main/resources/static/images/test1.jpg");
        testPicture1.setImage(testImage1);
        david.getPictures().add(testPicture1);
        testPicture1.setUser(david);

        testPicture2.setDescription("Test picture 2");
        testPicture2.setDateOfPost(LocalDateTime.of(2022, 10, 23, 12, 0));
        Byte[] testImage2 = ImageGetter.getImage("src/main/resources/static/images/test2.jpg");
        testPicture2.setImage(testImage2);
        david.getPictures().add(testPicture2);
        testPicture2.setUser(david);

        testPicture3.setDescription("Test picture 3");
        testPicture3.setDateOfPost(LocalDateTime.of(2022, 10, 25, 21, 41));
        Byte[] testImage3 = ImageGetter.getImage("src/main/resources/static/images/test3.jpg");
        testPicture3.setImage(testImage3);
        david.getPictures().add(testPicture3);
        testPicture3.setUser(david);

        userRepository.save(david);

        pictureRepository.save(testPicture1);
        pictureRepository.save(testPicture2);
        pictureRepository.save(testPicture3);

        List<Picture> pictures = pictureRepository.findAllByOrderByDateOfPostDesc();

        for (int i = 0;  i < pictures.size(); i++) {
            System.out.println(i + " id: " + pictures.get(i).getId() + " " + pictures.get(i).getDateOfPost());
        }
    }
}

