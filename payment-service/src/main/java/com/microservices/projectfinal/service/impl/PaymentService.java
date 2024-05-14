package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.config.PaymentConfigData;
import com.microservices.projectfinal.dto.VNPayResponseDTO;
import com.microservices.projectfinal.service.IPaymentService;
import com.microservices.projectfinal.utils.VnPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RequiredArgsConstructor
@Service
public class PaymentService implements IPaymentService {

    private final PaymentConfigData paymentConfigData;

    @Override
    public VNPayResponseDTO createVnPayPayment(HttpServletRequest request) {
        var vnPayConfigData = paymentConfigData.getVnpay();
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bank_code");
        Map<String, String> vnPayParams = getVNPayConfig(vnPayConfigData);
        vnPayParams.put("vnp_Amount", String.valueOf(amount));
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

    private Map<String, String> getVNPayConfig(PaymentConfigData.VnPay vnPayConfigData) {
        var txnRef = VnPayUtil.getRandomNumber(8);
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", vnPayConfigData.getVersion());
        vnpParamsMap.put("vnp_Command", vnPayConfigData.getCommand());
        vnpParamsMap.put("vnp_TmnCode", vnPayConfigData.getTmnCode());
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", txnRef);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + txnRef);
        vnpParamsMap.put("vnp_OrderType", vnPayConfigData.getOrderType());
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
