package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.microservices.projectfinal.entity.AccountEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@JsonRootName("tutor")
@Builder
@Getter
public class TutorResponseDTO {
    private String userId;
    private String email;
    private String firstName;
    private AccountEntity.Gender gender;
    private String lastName;
    private Date birth;
    private String address;
    private String avatarPath;
    private String subject;
    private BigDecimal teachFee;
    private Long timePerSession;
}
