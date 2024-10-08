package com.attractor.microgram.service.impl;

import com.attractor.microgram.dto.LikeDto;
import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.exception.CustomException;
import com.attractor.microgram.model.Like;
import com.attractor.microgram.repository.LikeRepository;
import com.attractor.microgram.repository.PublicationRepository;
import com.attractor.microgram.service.LikeService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class LikeServiceImpl implements LikeService {
    private final UserService userService;
    private final PublicationRepository publicationRepository;
    private final LikeRepository likeRepository;

    @Transactional
    @Override
    public void createLike(LikeDto data, Authentication authentication) {
        UserDto user = userService.getUserByAuth(authentication);
        Optional<Like> likeOptional = likeRepository.findByLikerIdAndPublicationId(user.getId(), data.getPublicationId());

        if (likeOptional.isPresent()) {
            log.error("Пользователь уже лайкнул пост с ID: {}", data.getPublicationId());
            throw new CustomException("Уже лайкнули этот пост");
        }

        Like like = new Like();
        like.setPublicationId(data.getPublicationId());
        like.setLikerId(user.getId());

        likeRepository.save(like);
        publicationRepository.addLike(data.getPublicationId());
    }
    @Transactional
    @Override
    public void deleteLike(LikeDto likeDto, Authentication authentication) {
        UserDto user = userService.getUserByAuth(authentication);
        Like like = likeRepository.findByLikerIdAndPublicationId(user.getId(), likeDto.getPublicationId())
                .orElseThrow(() -> new CustomException("Лайк не найден"));

        likeRepository.delete(like);
        publicationRepository.deleteLike(likeDto.getPublicationId());
    }

    @Override
    public LikeDto getLikeByPublicationIdAndAuthor(Long publicationId, Authentication authentication) {
        UserDto user = userService.getUserByAuth(authentication);

        Like like = likeRepository.findByLikerIdAndPublicationId(user.getId(), publicationId)
                .orElseThrow(() -> new CustomException("Данные " + publicationId + " не найдены"));

        return LikeDto.builder()
                .id(like.getId())
                .likerId(like.getLikerId())
                .publicationId(like.getPublicationId())
                .build();
    }

    @Override
    public boolean isUserExistByLike(Long publicationId, Long likeId) {
        return likeRepository.existsByPublicationIdAndLikerId(publicationId, likeId);
    }
}
