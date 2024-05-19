package com.microservices.projectfinal.job.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.projectfinal.kafka.comsumer.KafkaCDCModel;
import com.microservices.projectfinal.kafka.comsumer.PaymentLog;
import com.microservices.projectfinal.util.JsonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {
    @KafkaListener(
            topics = "${kafka.course.topic.payment-log}",
            groupId = "${kafka.course.group.payment-processor}"
    )
    public void listening(@Payload String message) throws JsonProcessingException {
        KafkaCDCModel kafkaCDCModel = JsonUtils.kafkaConnectUnmarshall(message);
        process(kafkaCDCModel.getAfter());
    }

    private void process(String data) {
        PaymentLog paymentLog = JsonUtils.parseJsonToObject(data, PaymentLog.class);
        System.out.println("Payment log: " + paymentLog.getData());
    }
}
