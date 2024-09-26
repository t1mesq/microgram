package com.attractor.microgram.repository;

import com.attractor.microgram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    List<User> findByNickName(String nickName);
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    List<User> findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.name LIKE %:query%")
    List<User> searchUsers(@Param("query") String query);
}
