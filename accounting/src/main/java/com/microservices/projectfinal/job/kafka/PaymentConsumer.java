package com.microservices.projectfinal.job.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.projectfinal.kafka.comsumer.KafkaCDCModel;
import com.microservices.projectfinal.kafka.comsumer.Payment;
import com.microservices.projectfinal.kafka.comsumer.PaymentLog;
import com.microservices.projectfinal.service.IBookingService;
import com.microservices.projectfinal.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@ConditionalOnProperty(
        value = "kafka.consumer.enabled",
        havingValue = "true"
)
public class PaymentConsumer {

    private final IBookingService bookingService;

    @KafkaListener(
            topics = "${kafka.call.topic.payment-log}",
            groupId = "${kafka.call.group.call-payment-processor}"
    )
    public void listening(@Payload String message) throws JsonProcessingException {
        KafkaCDCModel kafkaCDCModel = JsonUtils.kafkaConnectUnmarshall(message);
        process(kafkaCDCModel.getAfter());
    }

    private void process(String data) {
        PaymentLog paymentLog = JsonUtils.parseJsonToObject(data, PaymentLog.class);

        if (!paymentLog.getPaymentType().equals("CALL")) return;

        Payment payment = JsonUtils.parseJsonToObject(paymentLog.getData(), Payment.class);

        if (!payment.getPaymentStatus().equals("APPROVED")) return;

        bookingService.activeBooking(payment.getPaymentTransactionId(), payment.getUserId());
    }
}
