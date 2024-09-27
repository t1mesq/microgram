package com.attractor.microgram.controllers.mvc;

import com.attractor.microgram.dto.PublicationCreateDto;
import com.attractor.microgram.dto.PublicationEditDto;
import com.attractor.microgram.exception.FileUploadException;
import com.attractor.microgram.service.PublicationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class PublicationController {
    private final PublicationService publicationService;

    @PostMapping("publication/create")
    public ResponseEntity<String> createPublication(@ModelAttribute PublicationCreateDto newData, Authentication authentication, HttpSession session) {
        try {
            publicationService.createPublication(newData, authentication);
            return ResponseEntity.ok("Публикация успешно создана!");
        } catch (FileUploadException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при создании публикации. Попробуйте снова.");
        }
    }

    @PostMapping("publication/edit")
    public String editPublication(@ModelAttribute PublicationEditDto editDto, Authentication authentication) {
        publicationService.editPublication(editDto, authentication);
        return "redirect:/profile";
    }
}
