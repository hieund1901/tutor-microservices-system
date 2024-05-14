package com.microservices.projectfinal.dto;

import lombok.Builder;

@Builder
public class VNPayResponseDTO {
    public String code;
    public String message;
    public String paymentUrl;
}
