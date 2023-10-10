package com.david.caterest.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageRender {

    public static void renderImage(HttpServletResponse response, Byte[] image) throws IOException {
        if (image.length != 0) {
            byte[] byteArray = new byte[image.length];
            int i = 0;

            for (Byte wrappedByte : image)
                byteArray[i++] = wrappedByte; //auto unboxing

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
