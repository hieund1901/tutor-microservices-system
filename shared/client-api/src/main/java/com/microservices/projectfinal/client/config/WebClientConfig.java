package com.microservices.projectfinal.client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class WebClientConfig {
    @Value("${spring.security.user.name}")
    private String basicAuthUsername;
    @Value("${spring.security.user.password}")
    private String basicAuthPassword;

    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeaders(httpHeaders -> {
                    String auth = basicAuthUsername + ":" + basicAuthPassword;
                    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
                })
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
