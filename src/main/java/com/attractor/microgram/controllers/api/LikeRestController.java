package com.attractor.microgram.controllers.api;

import com.attractor.microgram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("likes")
public class LikeRestController {
    private final LikeService likeService;


    @PostMapping("/deleteLike")
    public ResponseEntity<Void> deleteLike(@RequestBody LikeDto likeDto, Authentication auth) {
        likeService.deleteLike(likeDto, auth);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/createLike")
    public ResponseEntity<Void> createLike(@RequestBody LikeDto likeDto, Authentication auth) {
        likeService.createLike(likeDto, auth);
        return ResponseEntity.noContent().build();
    }
}
