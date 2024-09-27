package com.attractor.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeDto {
    private Long id;
    private Long subscriberId;
    private Long userId;
    private String subscriberNickName;
    private String userNickName;
}
