package com.attractor.microgram.service;

import com.attractor.microgram.dto.SubscribeDto;
import com.attractor.microgram.model.Subscribe;

import java.util.List;

public interface SubscribeService {
    void subscribe(Long subscriberId, Long userId);
    void unsubscribe(Long subscriberId, Long userId);
    List<SubscribeDto> getSubscriptionsByUserId(Long userId);
    List<SubscribeDto> getSubscribersByUserId(Long userId);
    boolean isSubscribed(Long subscriberId, Long userId);
}
