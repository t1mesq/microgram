package com.attractor.microgram.controllers.api;

import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/download/{userId}")
    public ResponseEntity<?> downloadImage(@PathVariable("userId") long userId) {
        return userService.downloadImage(userId);
    }

    @PostMapping("/download/{userId}")
    public ResponseEntity<?> editImage(@PathVariable("userId") long userId) {
        return userService.downloadImage(userId);
    }

}
