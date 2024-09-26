package com.attractor.microgram.repository;

import com.attractor.microgram.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLikerIdAndPublicationId(Long likerId, Long publicationId);
    boolean existsByPublicationIdAndLikerId(Long publicationId, Long likerId);
}
