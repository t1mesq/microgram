package com.attractor.microgram.service.impl;

import com.attractor.microgram.dto.SubscribeDto;
import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.model.Subscribe;
import com.attractor.microgram.repository.SubscribeRepository;
import com.attractor.microgram.service.SubscribeService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class SubscribeServiceImpl implements SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final UserService userService;

    @Override
    public void subscribe(Long subscriberId, Long userId) {
        if (subscriberId.equals(userId)) {
            log.warn("Пользователь не может подписаться на самого себя");
            return;
        }

        boolean isAlreadySubscribed = isSubscribed(subscriberId, userId);

        if (!isAlreadySubscribed) {
            Subscribe subscribe = new Subscribe();
            subscribe.setSubscriberId(subscriberId);
            subscribe.setUserId(userId);
            subscribeRepository.save(subscribe);
        } else {
            log.warn("Пользователь с идентификатором {} уже подписан на пользователя с идентификатором {}", subscriberId, userId);
        }
    }

    @Override
    public void unsubscribe(Long subscriberId, Long userId) {
        subscribeRepository.deleteBySubscriberIdAndUserId(subscriberId, userId);
    }

    @Override
    public List<SubscribeDto> getSubscriptionsByUserId(Long userId) {
        List<Subscribe> subscriptions = subscribeRepository.findBySubscriberId(userId);

        return subscriptions.stream()
                .map(subscription -> {
                    UserDto user = userService.getUserById(subscription.getUserId());
                    return mapToDto(subscription, user.getNickName(), null);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscribeDto> getSubscribersByUserId(Long userId) {
        List<Subscribe> subscribers = subscribeRepository.findByUserId(userId);

        return subscribers.stream()
                .map(subscriber -> {
                    UserDto user = userService.getUserById(subscriber.getSubscriberId());
                    return mapToDto(subscriber, null, user.getNickName());
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSubscribed(Long subscriberId, Long userId) {
        return subscribeRepository.existsBySubscriberIdAndUserId(subscriberId, userId);
    }

    private SubscribeDto mapToDto(Subscribe subscribe, String userNickName, String subscriberNickName) {
        return SubscribeDto.builder()
                .id(subscribe.getId())
                .subscriberId(subscribe.getSubscriberId())
                .userId(subscribe.getUserId())
                .userNickName(userNickName)
                .subscriberNickName(subscriberNickName)
                .build();
    }
}

