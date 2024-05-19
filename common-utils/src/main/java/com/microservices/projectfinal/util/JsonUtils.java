package com.microservices.projectfinal.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.projectfinal.kafka.comsumer.KafkaCDCModel;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseJsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static KafkaCDCModel kafkaConnectUnmarshall(String message) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(message);
        KafkaCDCModel kafkaCDCModel = new KafkaCDCModel();
        kafkaCDCModel.setAfter(String.valueOf(jsonNode.get("after")));
        kafkaCDCModel.setBefore(String.valueOf(jsonNode.get("before")));
        kafkaCDCModel.setSource(String.valueOf(jsonNode.get("source")));
        return kafkaCDCModel;
    }
}
