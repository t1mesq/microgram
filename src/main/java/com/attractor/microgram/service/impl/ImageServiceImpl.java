package com.attractor.microgram.service.impl;

import com.attractor.microgram.exception.CustomException;
import com.attractor.microgram.service.ImageService;
import com.attractor.microgram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor

public class ImageServiceImpl implements ImageService {
    private final FileUtil fileUtil;

    @Override
    public String uploadAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.error("Attempted to upload an empty or null file");
            throw new IllegalArgumentException("File must not be empty or null");
        }

        try {
            return ImageService.uploadImage(file);
        } catch (Exception e) {
            log.error("An error occurred while uploading avatar: {}", e.getMessage());
            throw new CustomException("Unable to upload avatar");
        }
    }

    @Override
    public ResponseEntity<?> download(String name) {
        if (name == null || name.isEmpty()) {
            log.error("Attempted to download an image with an empty or null name");
            return ResponseEntity.badRequest().body("Image name must not be empty or null");
        }

        try {
            return ImageService.downloadImage(name, MediaType.IMAGE_JPEG);
        } catch (Exception e) {
            log.error("An error occurred while downloading image: {}", e.getMessage());
            throw new CustomException("Unable to download image with name " + name);
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadImage(String name) {
        if (name == null || name.isEmpty()) {
            log.error("Attempted to download an image with an empty or null name");
            return ResponseEntity.badRequest().body(null);
        }

        return fileUtil.getOutputFile(name, "/images/posts");
    }
}
