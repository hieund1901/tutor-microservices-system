package com.microservices.projectfinal.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TutorCreateRequest {
    private String subject;
    private BigDecimal teachFee;
    private Long timePerSession;
}
