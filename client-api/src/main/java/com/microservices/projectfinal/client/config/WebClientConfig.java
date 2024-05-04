package com.microservices.projectfinal.client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .codecs(clientCodecConfigurer -> {
                    ObjectMapper objectMapper  = Jackson2ObjectMapperBuilder.json()
                            .propertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE)
                            .serializationInclusion(JsonInclude.Include.NON_NULL)
                            .featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE)
                            .featuresToEnable(DeserializationFeature.UNWRAP_ROOT_VALUE)
                            .build();

                    clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                    clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                });
    }

}
