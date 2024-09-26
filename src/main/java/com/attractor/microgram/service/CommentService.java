package com.attractor.microgram.service;

import com.attractor.microgram.dto.CommentDto;
import com.attractor.microgram.dto.InsertCommentMessageDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments();
    List<CommentDto> getCommentsByPublication(Long publicationId, Authentication authentication);
    void deleteComment(Long publicationId,  Long commentId, Authentication authentication);
    void sendCommentMessage(InsertCommentMessageDto messageDto, Authentication auth);
    List<CommentDto> getCommentsByAuthorId(Long id);
    CommentDto getCommentById(long id);
}
