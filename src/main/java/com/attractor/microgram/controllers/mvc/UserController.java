package com.attractor.microgram.controllers.mvc;

import com.attractor.microgram.dto.UserInsertDto;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("register")
    public String create() {
        return "users/register";
    }

    @PostMapping("register")
    public String registerUser(UserInsertDto user) {
        Long userId = userService.createUser(user);
        return "redirect:/";
    }
}

