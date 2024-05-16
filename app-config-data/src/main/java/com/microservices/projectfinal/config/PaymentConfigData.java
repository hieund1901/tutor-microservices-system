package com.microservices.projectfinal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payment")
public class PaymentConfigData {
    private VnPay vnpay;

    @Data
    public static class VnPay {
        private String url;
        private String tmnCode;
        private String returnUrl;
        private String secretKey;
        private String version;
        private String command;
        private String apiUrl;
    }
}
