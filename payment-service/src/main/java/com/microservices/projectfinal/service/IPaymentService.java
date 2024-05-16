package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.VNPayResponseDTO;
import com.microservices.projectfinal.dto.VnpayCallbackParam;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface IPaymentService {
    VNPayResponseDTO createVnPayPayment(Long transactionId, BigDecimal amount, String bankCode, HttpServletRequest request);
    int processPaymentVnPayCallback(VnpayCallbackParam params);
}
