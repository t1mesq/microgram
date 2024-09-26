package com.attractor.microgram.controllers.mvc;

import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.dto.UserInsertDto;
import com.attractor.microgram.service.PublicationService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final PublicationService publicationService;

    @GetMapping("")
    public String create(Authentication authentication, Model model) {
        model.addAttribute("registerUser", new UserInsertDto());
        return "index";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam String query, Model model) {
        List<UserDto> users = userService.searchUsers(query);
        model.addAttribute("users", users);
        return "search_results";
    }
}

