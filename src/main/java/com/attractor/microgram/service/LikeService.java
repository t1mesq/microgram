package com.attractor.microgram.service;

import com.attractor.microgram.dto.LikeDto;
import org.springframework.security.core.Authentication;

public interface LikeService {
    void deleteLike(LikeDto likeDto, Authentication authentication);
    void createLike(LikeDto data, Authentication authentication);
    LikeDto getLikeByPublicationIdAndAuthor(Long publicationId, Authentication authentication);
    boolean isUserExistByLike(Long publicationId, Long likeId);
}
