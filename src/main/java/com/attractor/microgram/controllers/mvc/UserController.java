package com.attractor.microgram.controllers.mvc;

import com.attractor.microgram.dto.UserInsertDto;
import com.attractor.microgram.service.ImageService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("register")
    public String create() {
        return "users/register";
    }

    @PostMapping("register")
    public String registerUser(UserInsertDto user, @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null || file.isEmpty()) {
            user.setAvatar("anon.jpeg");
            System.out.println("Установлено дефолтное изображение: " + user.getAvatar());
        } else {
            String fileName = imageService.uploadAvatar(file);
            user.setAvatar(fileName);
        }

        Long userId = userService.createUser(user);
        System.out.println("Пользователь зарегистрирован с ID: " + userId + " и аватаром: " + user.getAvatar());
        return "redirect:/";
    }
}