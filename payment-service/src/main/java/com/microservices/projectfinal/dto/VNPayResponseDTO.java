package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@JsonRootName("vnpay_response")
@Getter
@Builder
public class VNPayResponseDTO {
    public String code;
    public String message;
    public String paymentUrl;
}
