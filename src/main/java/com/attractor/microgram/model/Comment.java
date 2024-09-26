package com.attractor.microgram.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "commentator_id")
    private Long commentatorId;

    @ManyToOne
    @JoinColumn(name = "publication_id", insertable = false, updatable = false)
    private Publication publication;

}
