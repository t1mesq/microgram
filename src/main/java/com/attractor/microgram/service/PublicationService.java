package com.attractor.microgram.service;

import com.attractor.microgram.dto.PublicationCreateDto;
import com.attractor.microgram.dto.PublicationDto;
import com.attractor.microgram.dto.PublicationEditDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PublicationService {
    List<PublicationDto> getPublications(Authentication authentication);
    List<PublicationDto> getPublicationsByAuthorId(Long id);
    PublicationDto getPublicationById(long id, Authentication auth);
    void deletePublication(long id, Authentication auth);
    PublicationDto getPublicationById(long id);
    List<PublicationDto> getPublicationsByAuthorIdAuth(Authentication auth);
    ResponseEntity<?> downloadImage(long id);
    void createPublication(PublicationCreateDto newData, Authentication authentication);
    void editPublication(PublicationEditDto editDto, Authentication authentication);
    int getTotalPublicationCount(Long userId);
}
