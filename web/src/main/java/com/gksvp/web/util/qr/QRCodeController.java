package com.gksvp.web.util.qr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class QRCodeController {

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @GetMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    public StreamingResponseBody generateQRCode(@RequestParam String text) throws Exception {
        return outputStream -> {
            BufferedImage image = null;
            try {
                image = qrCodeGenerator.generateQRCodeImage(text);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ImageIO.write(image, "PNG", outputStream);
        };
    }
}
