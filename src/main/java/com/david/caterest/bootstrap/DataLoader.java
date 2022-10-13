package com.david.caterest.bootstrap;

import com.david.caterest.entity.User;
import com.david.caterest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    //https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User david = new User();

        david.setUserName("DavidJ");

        userRepository.save(david);
    }

    public Byte[] getImage(String pathName) throws IOException {
        File fnew=new File(pathName);
        BufferedImage originalImage= ImageIO.read(fnew);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos );
        byte[] imageInByte=baos.toByteArray();
        Byte[] ImageInByte = new Byte[imageInByte.length];

        for (int i = 0; i < ImageInByte.length; i++) {
            ImageInByte[i] = Byte.valueOf(imageInByte[i]);
        }

        return ImageInByte;
    }
}

