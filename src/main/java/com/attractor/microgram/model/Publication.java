package com.attractor.microgram.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "publications")

public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "time_of_publication")
    private LocalDateTime timeOfPublication;

    @Column(name = "like_count")
    private Integer likeCount;
}
