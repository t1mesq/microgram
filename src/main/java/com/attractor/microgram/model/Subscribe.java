package com.attractor.microgram.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subscriptions")

public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "subscriber_id", insertable = false, updatable = false)
    private Long subscriberId;

    @Column(name = "user_id")
    private Long userId;

    private String subscriberNickName;
    private String userNickName;
}
