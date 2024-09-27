package com.attractor.microgram.service;

import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public interface ImageService {
    String UPLOAD_DIR = "data/images/";
    Path PATH = Path.of("data/images");
    String uploadAvatar(MultipartFile file);
    ResponseEntity<?> download(String name);

    @SneakyThrows
    static String uploadImage(MultipartFile file) {
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get(UPLOAD_DIR);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + resultFileName);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        }
        return resultFileName;
    }

    static ResponseEntity<?> downloadImage(String filename, MediaType mediaType) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(UPLOAD_DIR + filename));
            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        }
    }

    ResponseEntity<InputStreamResource> downloadImage(String name);
}

