package com.microservices.projectfinal.kafka.comsumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    private BigDecimal amount = BigDecimal.ZERO;
    private String currency = "VND";

    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("payment_status")
    private String paymentStatus;
    @JsonProperty("payment_transaction_type")
    private String paymentTransactionType;
    @JsonProperty("payment_transaction_id")
    private Long paymentTransactionId;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("modified_at")
    private Instant modifiedAt;
}
