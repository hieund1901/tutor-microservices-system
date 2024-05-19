package com.microservices.projectfinal.kafka.comsumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLog {
    private Long id;
    @JsonProperty("payment_id")
    private Long paymentId;
    @JsonProperty("transaction_id")
    private Long transactionId;
    @JsonProperty("payment_type")
    private String paymentType;

    private String verb;

    private String data;
    @JsonProperty("created_on")
    private Instant createdOn;
}
