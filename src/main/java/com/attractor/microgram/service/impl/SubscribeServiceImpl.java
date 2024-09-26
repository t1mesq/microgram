package com.attractor.microgram.service.impl;

import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.model.Subscribe;
import com.attractor.microgram.repository.SubscribeRepository;
import com.attractor.microgram.service.SubscribeService;
import com.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Subscribe> getSubscriptionsByUserId(Long userId) {
        List<Subscribe> subscriptions = subscribeRepository.findBySubscriberId(userId);
        for (Subscribe subscription : subscriptions) {
            UserDto user = userService.getUserById(subscription.getUserId());
            subscription.setUserNickName(user.getNickName());
        }
        return subscriptions;
    }

    @Override
    public List<Subscribe> getSubscribersByUserId(Long userId) {
        List<Subscribe> subscribers = subscribeRepository.findByUserId(userId);
        for (Subscribe subscriber : subscribers) {
            UserDto user = userService.getUserById(subscriber.getSubscriberId());
            subscriber.setSubscriberNickName(user.getNickName());
        }
        return subscribers;
    }

    @Override
    public boolean isSubscribed(Long subscriberId, Long userId) {
        return subscribeRepository.existsBySubscriberIdAndUserId(subscriberId, userId);
    }
}

