package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@JsonRootName("tutor")
public class TutorCreateRequest {
    private String subject;
    private BigDecimal teachFee;
    private String resume;
}
