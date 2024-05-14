package com.microservices.projectfinal.service;

import com.microservices.projectfinal.dto.VNPayResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    VNPayResponseDTO createVnPayPayment(HttpServletRequest request);
}
