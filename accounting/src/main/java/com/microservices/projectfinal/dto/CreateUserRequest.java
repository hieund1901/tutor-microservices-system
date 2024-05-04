package com.microservices.projectfinal.dto;

import com.microservices.projectfinal.entity.AccountEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CreateUserRequest {
    @Size(min = 6, max = 20)
    private String username;
    @Email
    @NotEmpty
    private String email;
    @Size(min = 6)
    private String password;
    @NotEmpty(message = "First name is required")
    private String firstName;

    private AccountEntity.Gender gender;

    private String lastName;
    private Date birth;
    private String address;
    private String avatarPath;
}
