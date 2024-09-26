package com.attractor.microgram.controllers.mvc;

import com.attractor.microgram.dto.PublicationCreateDto;
import com.attractor.microgram.dto.PublicationEditDto;
import com.attractor.microgram.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class PublicationController {
    private final PublicationService publicationService;


    @PostMapping("publication/create")
    public String createPublication(@ModelAttribute PublicationCreateDto newData, Authentication authentication) {
        publicationService.createPublication(newData, authentication);
        return "redirect:/profile";
    }

    @PostMapping("publication/edit")
    public String editPublication(@ModelAttribute PublicationEditDto editDto, Authentication authentication) {
        publicationService.editPublication(editDto, authentication);
        return "redirect:/profile";
    }
}
