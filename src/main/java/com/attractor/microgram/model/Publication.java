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

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private User author;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "time_of_publication")
    private LocalDateTime timeOfPublication;

    @Column(name = "like_count")
    private Integer likeCount;
}
