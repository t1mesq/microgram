package com.attractor.microgram.repository;

import com.attractor.microgram.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByAuthorId(Long authorId);
    int countByAuthorId(Long authorId);
    @Modifying
    @Query("UPDATE Publication p SET p.likeCount = p.likeCount - 1 WHERE p.id = ?1")
    void deleteLike(Long publicationId);
    @Modifying
    @Query("UPDATE Publication p SET p.likeCount = p.likeCount + 1 WHERE p.id = ?1")
    void addLike(Long publicationId); @Query("SELECT p.likeCount FROM Publication p WHERE p.id = ?1")
    Integer getCurrentLike(Long publicationId);
}
