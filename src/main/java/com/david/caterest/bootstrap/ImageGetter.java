package com.david.caterest.bootstrap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageGetter {

    public static Byte[] getImage(String pathName) throws IOException {
        File fileNew = new File(pathName);
        BufferedImage originalImage = ImageIO.read(fileNew );
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", byteArr );
        byte[] imageInByte = byteArr.toByteArray();
        Byte[] ImageInByte = new Byte[imageInByte.length];

        for (int i = 0; i < ImageInByte.length; i++) {
            ImageInByte[i] = imageInByte[i];
        }

        return ImageInByte;
    }
}
