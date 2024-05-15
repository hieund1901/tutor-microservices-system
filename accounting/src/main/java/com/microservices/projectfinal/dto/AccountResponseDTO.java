package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
@JsonRootName("account")
@Builder
@Getter
public class AccountResponseDTO {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private Instant birth;

    private String gender;

    private String address;

    private String avatarPath;

    private Instant createdAt;

    private Instant modifiedAt;
}
