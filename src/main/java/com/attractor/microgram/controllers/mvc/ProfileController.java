package com.attractor.microgram.controllers.mvc;

import com.project.microgram.dto.UserDto;
import com.project.microgram.dto.UserInsertDto;
import com.project.microgram.service.PublicationService;
import com.project.microgram.service.SubscribeService;
import com.project.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class ProfileController {
    private final UserService userService;
    private final PublicationService publicationService;
    private final SubscribeService subscribeService;

    @GetMapping("profile")
    public String showProfile(Model model, Authentication authentication) {
        UserDto user = userService.getUserByAuth(authentication);
        model.addAttribute("user", user);
        model.addAttribute("publications", publicationService.getPublicationsByAuthorIdAuth(authentication));
        model.addAttribute("publicationCount", publicationService.getTotalPublicationCount(user.getId()));
        return "profile/profile";
    }


    @PostMapping("profile/edit")
    public String editProfile(@ModelAttribute UserInsertDto newData, Authentication authentication) {
        long userId = userService.getUserByAuth(authentication).getId();
        userService.updateUserProfile(userId, newData);
        return "redirect:/profile";
    }

    @GetMapping("profile/{id}")
    public String showUser(@PathVariable Long id, Authentication authentication, Model model) {
        UserDto optionalUser = userService.getUserById(id, authentication);
        model.addAttribute("user", optionalUser);
        model.addAttribute("publications", publicationService.getPublicationsByAuthorId(optionalUser.getId()));
        model.addAttribute("publicationCount", publicationService.getTotalPublicationCount(optionalUser.getId())); // Используем optionalUser.getId()

        UserDto currentUser = userService.getCurrentUser(authentication);
        if (currentUser != null) {
            boolean isSubscribed = subscribeService.isSubscribed(currentUser.getId(), id);
            model.addAttribute("isSubscribed", isSubscribed);
        }

        return "search_profile";
    }
}
