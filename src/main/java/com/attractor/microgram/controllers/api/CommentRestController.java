package com.attractor.microgram.controllers.api;

import com.attractor.microgram.dto.CommentDto;
import com.attractor.microgram.dto.InsertCommentMessageDto;
import com.attractor.microgram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("{publicationId}")
    public List<CommentDto> getCommentsById(@PathVariable Long publicationId, Authentication auth) {
        List<CommentDto> data= commentService.getCommentsByPublication(publicationId, auth);
        return data;
    }

    @GetMapping("/deleteComment/{publicationId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long publicationId,@PathVariable Long commentId, Authentication auth) {
        commentService.deleteComment(publicationId,commentId, auth);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sendCommentMessage")
    public ResponseEntity<Void> sendCommentMessage(@RequestBody InsertCommentMessageDto messageDto, Authentication auth) {
        commentService.sendCommentMessage(messageDto, auth);
        return ResponseEntity.noContent().build();
    }
}
