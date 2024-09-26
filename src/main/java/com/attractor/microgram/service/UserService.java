package com.attractor.microgram.service;

import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.dto.UserInsertDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    List<UserDto> getUsersByName(String name);
    List<UserDto> getUsersByNickName(String name);
    List<UserDto> getUsersByPhoneNumber(String phoneNumber);
    UserDto getUsersByEmail(String email);
    boolean isUserExistByEmail(String email);
    boolean isUserExistByNickName(String name);
    Long createUser(UserInsertDto data);
    UserDto getUserById(long id);
    UserDto getUserById(long id, Authentication auth);
    void login(Authentication auth);
    UserDto getUserByAuth(Authentication auth);
    ResponseEntity<?> downloadImage(long userId);
    void updateUserProfile(long userId, UserInsertDto newData);
    List<UserDto> searchUsers(String query);
    UserDto getCurrentUser(Authentication auth);
}