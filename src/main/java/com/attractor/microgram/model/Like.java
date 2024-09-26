package com.attractor.microgram.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "likes")

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "liker_id")
    private Long likerId;

    @ManyToOne
    @JoinColumn(name = "publication_id", insertable = false, updatable = false)
    private Publication publication;

}
