package com.lordscave.societyxapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Slf4j
@Component
public class ImageStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${server.url}")
    private String serverUrl;

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
        log.info("Uploading file: {}", file.getOriginalFilename());

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String folderName = new File(uploadDir).getName();

            return serverUrl + "/" + folderName + "/" + fileName;

        } catch (IOException e) {
            log.error("Failed to upload file", e);
            throw new RuntimeException("Failed to upload file", e);
        }
    }

}
