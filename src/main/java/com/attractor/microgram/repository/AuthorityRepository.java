package com.attractor.microgram.repository;

import com.attractor.microgram.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRole(String role);
}
