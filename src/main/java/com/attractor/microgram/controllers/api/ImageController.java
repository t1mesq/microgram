package com.attractor.microgram.controllers.api;

import com.attractor.microgram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("imageRest")
@RequiredArgsConstructor
@RequestMapping("api/images")

public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String > upload(MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(imageService.uploadAvatar(file));
    }

    @GetMapping("byName")
    public ResponseEntity<?> download(@RequestParam(name = "name")String name){
        return imageService.download(name);
    }

    @GetMapping(value = "image/{name}")
    public ResponseEntity<InputStreamResource> get(@PathVariable  String name){
        return imageService.downloadImage(name);
    }

}
