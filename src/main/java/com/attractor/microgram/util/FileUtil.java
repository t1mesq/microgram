package com.attractor.microgram.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    private static final String UPLOAD_DIR ="data/";
    private static final String UPLOAD_DIRLocal ="./resources/templates/auth/images";

    @SneakyThrows
    public String saveUploadedFile(MultipartFile file, String subDir){
        String resultFileName=UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

        Path pathDir= Paths.get(UPLOAD_DIR+subDir);
        Files.createDirectories(pathDir);

        Path filePath= Paths.get(pathDir +"/"+resultFileName);
        if(!Files.exists(filePath)){
            Files.createFile(filePath);
        }

        try (OutputStream os = Files.newOutputStream(filePath)){
            os.write(file.getBytes());
        } catch (IIOException e){
            log.error(e.getMessage());
        }

        return resultFileName;
    }

    @SneakyThrows
    public ResponseEntity<?> getOutputFile(String fileName, String subDir, MediaType mediaType){
        try {
            Path path=Paths.get(UPLOAD_DIR+ subDir+"/"+fileName);
            byte[] image = Files.readAllBytes(path);
            ByteArrayResource res= new ByteArrayResource(image);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\""+fileName+"\"")
                    .contentLength(res.contentLength()).contentType(mediaType).body(res);
        }catch (NoSuchFileException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        }
    }

    @SneakyThrows
    public ResponseEntity<?> getOutputStaticFile(String fileName, String subDir, MediaType mediaType){
        try {
            ClassPathResource resource = new ClassPathResource("/static/"+subDir+"/"+fileName);
            byte[] image = resource.getContentAsByteArray();
            ByteArrayResource res= new ByteArrayResource(image);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\""+fileName+"\"")
                    .contentLength(res.contentLength()).contentType(mediaType).body(res);
        }catch (NoSuchFileException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        }
    }

    public ResponseEntity<InputStreamResource> getOutputFile(String fileName, String subDir) {
        try {
            Path path = Paths.get(UPLOAD_DIR + subDir + "/" + fileName);
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
            MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(path));
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new InputStreamResource(resource.getInputStream()));
        } catch (IOException e) {
            log.error("No file found:", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
