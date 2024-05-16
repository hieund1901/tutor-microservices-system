package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.config.PaymentConfigData;
import com.microservices.projectfinal.dto.VNPayResponseDTO;
import com.microservices.projectfinal.dto.VnpayCallbackParam;
import com.microservices.projectfinal.entity.PaymentEntity;
import com.microservices.projectfinal.exception.ResponseException;
import com.microservices.projectfinal.repository.PaymentRepository;
import com.microservices.projectfinal.repository.PaymentTransactionRepository;
import com.microservices.projectfinal.service.IPaymentService;
import com.microservices.projectfinal.utils.VnPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RequiredArgsConstructor
@Service
public class PaymentService implements IPaymentService {

    private final PaymentConfigData paymentConfigData;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public VNPayResponseDTO createVnPayPayment(Long transactionId, BigDecimal amount, String bankCode, HttpServletRequest request) {
        var vnPayConfigData = paymentConfigData.getVnpay();
        BigDecimal amountParse = amount.multiply(new BigDecimal(100));
        Map<String, String> vnPayParams = getVNPayConfig(vnPayConfigData, transactionId);
        vnPayParams.put("vnp_Amount", String.valueOf(amountParse));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnPayParams.put("vnp_BankCode", bankCode);
        }

        vnPayParams.put("vnp_IpAddr", VnPayUtil.getIpAddress(request));

        String queryUrl = VnPayUtil.getPaymentURL(vnPayParams, true);
        String hashData = VnPayUtil.getPaymentURL(vnPayParams, false);

        String secureHash = VnPayUtil.hmacSHA512(vnPayConfigData.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + secureHash;
        String paymentUrl = vnPayConfigData.getUrl() + "?" + queryUrl;
        return VNPayResponseDTO.builder()
                .code("ok")
                .message("Success")
                .paymentUrl(paymentUrl)
                .build();
    }

    @Override
    public int processPaymentVnPayCallback(VnpayCallbackParam params) {
        if (!"00".equals(params.getVnp_ResponseCode())) {
            throw new ResponseException("Payment failed", HttpStatus.BAD_REQUEST);
        }
        var transactionId = Long.parseLong(params.getVnp_TxnRef().split("_")[1]);
        var paymentOrderType = params.getVnp_OrderInfo();

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentMethod("VNPay")
                .paymentStatus(PaymentEntity.PaymentStatus.APPROVED)
                .paymentTransactionId(transactionId)
                .amount(new BigDecimal(params.getVnp_Amount()))
                .currency("VND")
                .paymentTransactionType(paymentOrderType)
                .build();

        paymentRepository.save(paymentEntity);
        return 1;
    }

    private Map<String, String> getVNPayConfig(PaymentConfigData.VnPay vnPayConfigData, Long transactionId) {
        var txnRef = VnPayUtil.getRandomNumber(8);
        var paymentTransaction = paymentTransactionRepository.findById(transactionId).orElseThrow(
                () -> new ResponseException("Transaction not found", HttpStatus.NOT_FOUND)
        );
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", vnPayConfigData.getVersion());
        vnpParamsMap.put("vnp_Command", vnPayConfigData.getCommand());
        vnpParamsMap.put("vnp_TmnCode", vnPayConfigData.getTmnCode());
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", txnRef + "_" + transactionId);
        vnpParamsMap.put("vnp_OrderInfo", paymentTransaction.getReferenceType().name());
        vnpParamsMap.put("vnp_OrderType", paymentTransaction.getReferenceType().name());
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", vnPayConfigData.getReturnUrl());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }


}
