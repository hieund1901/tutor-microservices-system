package com.microservices.projectfinal.controller;

import com.microservices.projectfinal.dto.VNPayResponseDTO;
import com.microservices.projectfinal.service.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/integration")
public class PaymentController {

    private final IPaymentService paymentService;

    @GetMapping("/vn-pay")
    public ResponseEntity<VNPayResponseDTO> pay(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<VNPayResponseDTO> callback(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        return ResponseEntity.ok(VNPayResponseDTO.builder()
                .code(status)
                .message("Payment " + ("00".equals(status) ? "success" : "failed"))
                .build());
    }

}
