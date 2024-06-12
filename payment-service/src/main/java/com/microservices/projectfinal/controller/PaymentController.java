package com.microservices.projectfinal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.projectfinal.dto.VNPayResponseDTO;
import com.microservices.projectfinal.dto.VnpayCallbackParam;
import com.microservices.projectfinal.service.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/integration")
public class PaymentController {

    private final IPaymentService paymentService;

    @GetMapping("/vn-pay/{transactionId}")
    public ResponseEntity<VNPayResponseDTO> pay(
            @PathVariable Long transactionId,
            @RequestParam("amount")BigDecimal amount,
            @RequestParam("bank_code") String bankCode,
            HttpServletRequest request) {
        return ResponseEntity.ok(paymentService.createVnPayPayment(transactionId, amount, bankCode, request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<Void> callback(@ModelAttribute VnpayCallbackParam params) throws JsonProcessingException {
        var redirectUrl = paymentService.processPaymentVnPayCallback(params);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

}
