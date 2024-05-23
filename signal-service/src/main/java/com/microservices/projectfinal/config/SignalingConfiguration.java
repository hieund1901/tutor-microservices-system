package com.microservices.projectfinal.config;

import com.microservices.projectfinal.handler.SignalingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class SignalingConfiguration implements WebSocketConfigurer {
    @Value("${allowed.origins:*}")
    private String allowedOrigins;
    private final SignalingHandler signalingHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalingHandler, "/socket")
                .setAllowedOrigins(allowedOrigins);
    }
}
