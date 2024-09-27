package com.attractor.microgram.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDto {

    @NotBlank(message = "name не может быть пустым")
    private String name;
    private String surname;
    private String bio;
    @Positive
    private Integer age;

    @NotBlank(message = "email не может быть пустым")
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 8, message = "Password must be from 4 to 8 symbols")
    private String password;
    @NotBlank
    @Size(min = 4, max = 8, message = "Password must be from 4 to 8 symbols")
    private String confirmationPassword;

    @NotBlank(message = "phoneNumber не может быть пустым")
    private String phoneNumber;
    @NotBlank(message = "nickname не может быть пустым")
    private String nickName;
    private MultipartFile file;
    private String avatar;
}
