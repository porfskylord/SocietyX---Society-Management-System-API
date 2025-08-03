package com.lordscave.societyxapi.utils;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class ImageStorageServiceTest {

    @Autowired
    private ImageStorageService imageStorageService;

    @Test
    void testImageUpload() throws IOException {
        // Path to your local image (make sure it exists)
        String imagePath = "D:/JAVAPROGRAMMING/ALL Projects/societyxapi/images/Gard.jpeg";

        // Convert to MultipartFile using Spring's MockMultipartFile
        FileInputStream input = new FileInputStream(imagePath);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "AzadProfile.jpeg",
                "image/jpeg",
                input
        );

        // Call upload method
        String uploadedUrl = imageStorageService.upload(multipartFile);

        System.out.println("Image uploaded to: " + uploadedUrl);
    }
}
