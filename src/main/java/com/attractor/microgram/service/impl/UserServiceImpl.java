package com.attractor.microgram.service.impl;

import com.attractor.microgram.dto.UserDto;
import com.attractor.microgram.dto.UserInsertDto;
import com.attractor.microgram.exception.CustomException;
import com.attractor.microgram.model.Authority;
import com.attractor.microgram.model.User;
import com.attractor.microgram.repository.AuthorityRepository;
import com.attractor.microgram.repository.UserRepository;
import com.attractor.microgram.service.UserService;
import com.attractor.microgram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final String dirForFile = "images";
    private final PasswordEncoder encoder;
    private final AuthorityRepository authorityRepository;
    private final String CLIENT_AUTHORITY = "client";

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return mapByUsersDto(users);
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        List<User> users = userRepository.findByName(name);
        return mapByUsersDto(users);
    }

    @Override
    public List<UserDto> getUsersByNickName(String nickName) {
        List<User> users = userRepository.findByNickName(nickName);
        return mapByUsersDto(users);
    }

    @Override
    public List<UserDto> getUsersByPhoneNumber(String phoneNumber) {
        List<User> users = userRepository.findByPhoneNumber(phoneNumber);
        return mapByUsersDto(users);
    }

    @Override
    public UserDto getUsersByEmail(String email) {
        return usersByEmail(email, false);
    }

    private UserDto usersByEmail(String email, Boolean isCurrentUser) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            String message = "Пользователь с таким email: " + email + " не найден";
            log.error(message);
            throw new CustomException(message);
        }
        return mapToDto(userOptional.get(), isCurrentUser);
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        List<User> users = userRepository.searchUsers(query);
        return mapByUsersDto(users);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUserExistByNickName(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public Long createUser(UserInsertDto data) {
        if (!data.getPassword().equals(data.getConfirmationPassword())) {
            String error = "Пароли не совпадают";
            log.error(error);
            throw new CustomException(error);
        }

        if (isUserExistByEmail(data.getEmail())) {
            String error = "Данный email уже существует в системе: " + data.getEmail();
            log.error(error);
            throw new CustomException(error);
        }

        if (isUserExistByNickName(data.getNickName())) {
            String error = "Данный никнейм уже существует в системе: " + data.getNickName();
            log.error(error);
            throw new CustomException(error);
        }

        if (data.getName() == null || data.getName().isBlank()) {
            String error = "Имя не может быть пустым";
            log.error(error);
            throw new CustomException(error);
        }

        User user = new User();
        user.setName(data.getName());
        user.setSurname(data.getSurname());
        user.setBio(data.getBio());
        user.setAge(data.getAge());
        user.setEmail(data.getEmail());
        user.setPassword(encoder.encode(data.getPassword()));
        user.setPhoneNumber(data.getPhoneNumber());
        user.setNickName(data.getNickName());

        if (data.getFile() != null) {
            String fileName = fileUtil.saveUploadedFile(data.getFile(), dirForFile);
            user.setAvatar(fileName);
        }

        Authority authority = authorityRepository.findByRole("client")
                .orElseGet(() -> {
                    Authority newAuthority = new Authority();
                    newAuthority.setRole("client");
                    return authorityRepository.save(newAuthority);
                });
        System.out.println(authority.getRole());
        System.out.println(authority.getId());

        user.setAuthority(authority);

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public void updateUserProfile(long userId, UserInsertDto data) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            String message = "Пользователь с ID " + userId + " не найден";
            log.error(message);
            return new CustomException(message);
        });

        user.setNickName(data.getNickName());
        user.setName(data.getName());
        user.setSurname(data.getSurname());
        user.setBio(data.getBio());
        user.setAge(data.getAge());
        user.setPhoneNumber(data.getPhoneNumber());

        if (data.getFile() != null) {
            String fileName = fileUtil.saveUploadedFile(data.getFile(), dirForFile);
            user.setAvatar(fileName);
        }

        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow();
        return mapToDto(user, false);
    }

    @Override
    public UserDto getUserById(long id, Authentication auth) {
        boolean isCurrentUser = false;
        if (auth != null) {
            UserDto currentUser = getUserByAuth(auth);
            if (currentUser.getId().equals(id)) {
                isCurrentUser = true;
            }
        }

        User user = userRepository.findById(id).orElseThrow();
        return mapToDto(user, isCurrentUser);
    }

    private UserDto mapToDto(User data, Boolean isCurrentUser) {
        return UserDto.builder()
                .id(data.getId())
                .name(data.getName())
                .surname(data.getSurname())
                .age(data.getAge())
                .email(data.getEmail())
                .phoneNumber(data.getPhoneNumber())
                .avatar(data.getAvatar())
                .nickName(data.getNickName())
                .role("client")
                .bio(data.getBio())
                .isCurrentUser(isCurrentUser)
                .build();
    }

    public List<UserDto> mapByUsersDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(e -> userDtos.add(mapToDto(e, false)));
        return userDtos;
    }

    @Override
    public void login(Authentication auth) {
        log.info(auth.getPrincipal().toString());
    }

    @Override
    public UserDto getUserByAuth(Authentication auth) {
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return usersByEmail(u.getUsername(), true);
    }

    @Override
    public ResponseEntity<?> downloadImage(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return fileUtil.getOutputFile(user.get().getAvatar(), dirForFile, MediaType.IMAGE_JPEG);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User not found");
    }

    @Override
    public UserDto getCurrentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        return getUserByAuth(auth);
    }
}

