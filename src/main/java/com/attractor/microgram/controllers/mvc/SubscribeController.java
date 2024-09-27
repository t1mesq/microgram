package com.attractor.microgram.controllers.mvc;

import com.attractor.microgram.dto.SubscribeDto;
import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.model.Subscribe;
import com.attractor.microgram.service.SubscribeService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;
    private final UserService userService;

    @PostMapping("/subscribe")
    public String subscribeUser(Authentication authentication, @RequestParam Long userId) {
        UserDto currentUser = userService.getUserByAuth(authentication);
        Long subscriberId = currentUser.getId();

        subscribeService.subscribe(subscriberId, userId);

        return "redirect:/";
    }

    @PostMapping("/unsubscribe")
    public String unsubscribeUser(Authentication authentication, @RequestParam Long userId) {
        UserDto currentUser = userService.getUserByAuth(authentication);
        Long subscriberId = currentUser.getId();

        subscribeService.unsubscribe(subscriberId, userId);

        return "redirect:/";
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<Map<String, Object>> getUserSubscriptions(Authentication authentication) {
        UserDto currentUser = userService.getUserByAuth(authentication);
        Long userId = currentUser.getId();

        List<SubscribeDto> subscriptions = subscribeService.getSubscriptionsByUserId(userId);
        List<SubscribeDto> subscribers = subscribeService.getSubscribersByUserId(userId);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("subscriptions", subscriptions);
        responseData.put("subscribers", subscribers);

        return ResponseEntity.ok(responseData);
    }
}
