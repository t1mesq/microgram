package com.attractor.microgram.controllers.api;

import com.attractor.microgram.dto.PublicationDto;
import com.attractor.microgram.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("publications")
public class PublicationRestController {
    private final PublicationService publicationService;

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable("id") long id) {
        return publicationService.downloadImage(id);
    }


    @GetMapping("{id}")
    public ResponseEntity<PublicationDto> getPublicationsById(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(publicationService.getPublicationById(id, auth));
    }

    @GetMapping("/deletePublication/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id, Authentication auth) {
        publicationService.deletePublication(id, auth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getPublications")
    public List<PublicationDto> getPublications(Authentication auth) {
        return publicationService.getPublications(auth);
    }
}
