package com.microservices.projectfinal.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.projectfinal.dto.SignalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class SignalingHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        SignalData signalData = objectMapper.readValue(payload, SignalData.class);
        log.info("Received signal data: {}", signalData);
        if (signalData.getSignalType() == SignalData.SignalType.Offer) {
            sessionMap.put(signalData.getToUserId(), session);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(signalData)));
        }
    }

}
