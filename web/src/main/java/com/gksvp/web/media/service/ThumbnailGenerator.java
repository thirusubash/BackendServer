package com.gksvp.web.media.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailGenerator {

    @Value("${thumbnail.width}")
    private int thumbnailWidth;

    @Value("${thumbnail.height}")
    private int thumbnailHeight;

    @Value("${thumbnail.outputExtension}")
    private String thumbnailOutputExtension;

    public byte[] generateThumbnail(byte[] imageData, String contentType) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage originalImage = ImageIO.read(inputStream);

        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage thumbnailImage = new BufferedImage(thumbnailWidth, thumbnailHeight, type);
        Graphics2D graphics2D = thumbnailImage.createGraphics();
        graphics2D.drawImage(
                originalImage.getScaledInstance(thumbnailWidth, thumbnailHeight, BufferedImage.SCALE_SMOOTH),
                0, 0, thumbnailWidth, thumbnailHeight, null);
        graphics2D.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(thumbnailImage, thumbnailOutputExtension, outputStream);
        return outputStream.toByteArray();
    }
}
