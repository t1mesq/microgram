package com.attractor.microgram.service.impl;

import com.attractor.microgram.dto.CommentDto;
import com.attractor.microgram.dto.InsertCommentMessageDto;
import com.attractor.microgram.dto.PublicationDto;
import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.model.Comment;
import com.attractor.microgram.repository.CommentRepository;
import com.attractor.microgram.service.CommentService;
import com.attractor.microgram.service.PublicationService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PublicationService publicationService;

    @Override
    public List<CommentDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        return mapByCommentDto(comments, false);
    }

    @Override
    public List<CommentDto> getCommentsByPublication(Long publicationId, Authentication authentication) {
        boolean isAuthorPost = false;
        if (authentication != null) {
            UserDto user = userService.getUserByAuth(authentication);
            PublicationDto publicationDto = publicationService.getPublicationById(publicationId);
            if (publicationDto.getAuthorId().equals(user.getId())) {
                isAuthorPost = true;
            }
        }

        List<Comment> comments = commentRepository.findByPublicationId(publicationId);
        return mapByCommentDto(comments, isAuthorPost);
    }

    @Override
    public void deleteComment(Long publicationId, Long commentId, Authentication authentication) {
        PublicationDto publicationDto = publicationService.getPublicationById(publicationId, authentication);
        commentRepository.deleteById(commentId);
    }

    @Override
    public void sendCommentMessage(InsertCommentMessageDto messageDto, Authentication auth) {
        UserDto user = userService.getUserByAuth(auth);
        PublicationDto publicationDto = publicationService.getPublicationById(messageDto.getPublicationId());

        Comment comment = new Comment();
        comment.setCommentatorId(user.getId());
        comment.setText(messageDto.getText());
        comment.setPublicationId(publicationDto.getId());

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getCommentsByAuthorId(Long id) {
        List<Comment> comments = commentRepository.findByCommentatorId(id);
        return mapByCommentDto(comments, false);
    }

    @Override
    public CommentDto getCommentById(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow();
        return mapToDto(comment, false);
    }

    private CommentDto mapToDto(Comment data, boolean isAuthorPost) {
        UserDto user = userService.getUserById(data.getCommentatorId());
        return CommentDto.builder()
                .id(data.getId())
                .text(data.getText())
                .commentator(user.getNickName())
                .isAuthorPost(isAuthorPost)
                .build();
    }

    private List<CommentDto> mapByCommentDto(List<Comment> comments, boolean isAuthorPost) {
        List<CommentDto> commentDtos = new ArrayList<>();
        comments.forEach(e -> commentDtos.add(mapToDto(e, isAuthorPost)));
        return commentDtos;
    }
}
