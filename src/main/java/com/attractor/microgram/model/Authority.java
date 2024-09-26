package com.attractor.microgram.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authorities")

public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "authority")
    private List<User> users;
}
