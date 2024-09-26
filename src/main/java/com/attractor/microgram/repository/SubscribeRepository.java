package com.attractor.microgram.repository;

import com.attractor.microgram.model.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<Subscribe> findBySubscriberId(Long subscriberId);
    List<Subscribe> findByUserId(Long userId);
    void deleteBySubscriberIdAndUserId(Long subscriberId, Long userId);
    boolean existsBySubscriberIdAndUserId(Long subscriberId, Long userId);
}
