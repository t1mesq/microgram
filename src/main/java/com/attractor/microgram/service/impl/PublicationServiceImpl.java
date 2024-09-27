package com.attractor.microgram.service.impl;

import com.attractor.microgram.dto.PublicationCreateDto;
import com.attractor.microgram.dto.PublicationDto;
import com.attractor.microgram.dto.PublicationEditDto;
import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.exception.CustomException;
import com.attractor.microgram.exception.FileUploadException;
import com.attractor.microgram.model.Publication;
import com.attractor.microgram.repository.PublicationRepository;
import com.attractor.microgram.service.LikeService;
import com.attractor.microgram.service.PublicationService;
import com.attractor.microgram.service.UserService;
import com.attractor.microgram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final LikeService likeService;
    private final String dirForFile = "images/posts";

    @Override
    public List<PublicationDto> getPublications(Authentication authentication) {
        UserDto user = null;
        if (authentication != null) {
            user = userService.getUserByAuth(authentication);
        }

        List<Publication> publications = publicationRepository.findAll();
        return mapByPublicationDto(publications, user);
    }

    @Override
    public List<PublicationDto> getPublicationsByAuthorId(Long id) {
        List<Publication> publications = publicationRepository.findByAuthorId(id);
        return mapByPublicationDto(publications, null);
    }

    @Override
    public PublicationDto getPublicationById(long id, Authentication auth) {
        UserDto userDto = null;
        if (auth != null) {
            userDto = userService.getUserByAuth(auth);
        }
        return getPublicationById(id, userDto);
    }

    @Override
    public PublicationDto getPublicationById(long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Can not find this post"));
        return mapToDto(publication, null);
    }

    public PublicationDto getPublicationById(long id, UserDto userDto) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Can not find this post"));
        return mapToDto(publication, userDto);
    }

    @Override
    public void deletePublication(long id, Authentication auth) {
        publicationRepository.deleteById(id);
    }

    @Override
    public List<PublicationDto> getPublicationsByAuthorIdAuth(Authentication auth) {
        return getPublicationsByAuthorId(userService.getUserByAuth(auth).getId());
    }

    private PublicationDto mapToDto(Publication data, UserDto userDto) {
        boolean isLike = false;
        if (userDto != null) {
            isLike = likeService.isUserExistByLike(data.getId(), userDto.getId());
        }
        return PublicationDto.builder()
                .id(data.getId())
                .authorId(data.getAuthorId())
                .image(data.getImage())
                .description(data.getDescription())
                .timeOfPublication(data.getTimeOfPublication())
                .isLike(isLike)
                .likeCount(data.getLikeCount())
                .build();
    }

    public List<PublicationDto> mapByPublicationDto(List<Publication> publications, UserDto userDto) {
        List<PublicationDto> publicationDtos = new ArrayList<>();
        publications.forEach(e -> publicationDtos.add(mapToDto(e, userDto)));
        return publicationDtos;
    }

    @Override
    public ResponseEntity<?> downloadImage(long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));
        return fileUtil.getOutputFile(publication.getImage(), dirForFile, MediaType.IMAGE_JPEG);
    }

    @Override
    public void createPublication(PublicationCreateDto newData, Authentication authentication) {
        UserDto userDto = userService.getUserByAuth(authentication);
        Publication publication = new Publication();
        publication.setAuthorId(userDto.getId());
        publication.setDescription(newData.getDescription());

        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

        if (newData.getImage() != null) {
            String originalFilename = newData.getImage().getOriginalFilename();
            if (originalFilename != null) {
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

                if (!allowedExtensions.contains(fileExtension)) {
                    throw new FileUploadException("Неверный тип файла. Пожалуйста, загрузите изображение формата: jpg, jpeg, png, gif.");
                }

                String fileName = fileUtil.saveUploadedFile(newData.getImage(), dirForFile);
                publication.setImage(fileName);
            }
        }

        publicationRepository.save(publication);
    }

    @Override
    public void editPublication(PublicationEditDto editDto, Authentication authentication) {
        if (editDto.getDescription() == null || editDto.getDescription().isBlank()) {
            throw new CustomException("Description is null");
        }

        Publication publication = publicationRepository.findById(editDto.getId())
                .orElseThrow(() -> new CustomException("Post not found"));

        publication.setDescription(editDto.getDescription());
        publicationRepository.save(publication);
    }

    @Override
    public int getTotalPublicationCount(Long userId) {
        return publicationRepository.countByAuthorId(userId);
    }
}
