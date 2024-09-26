package com.attractor.microgram.repository;

import com.attractor.microgram.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPublicationId(Long publicationId);
    List<Comment> findByCommentatorId(Long commentatorId);
}
