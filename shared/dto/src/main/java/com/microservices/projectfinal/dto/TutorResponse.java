package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@JsonRootName("tutor")
@Getter
@Builder
public class TutorResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private Date birthDate;
    private String avatarPath;
    private String address;
    private String subject;
    private long timePerOnePurchase;
    private BigDecimal teachFee;
    private long accountId;
    private String userId;
    private Date joinedAt;
}
