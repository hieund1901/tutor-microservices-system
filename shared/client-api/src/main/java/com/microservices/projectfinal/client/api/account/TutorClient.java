package com.microservices.projectfinal.client.api.account;

import com.microservices.projectfinal.config.WebclientConfigData;
import com.microservices.projectfinal.dto.TutorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class    TutorClient {
    private static final String TUTOR_URL = "/accounting/tutor";
    private final WebClient.Builder webClientBuilder;
    private final WebclientConfigData webClientConfigData;

    public TutorClient(WebClient.Builder webClientBuilder, WebclientConfigData webClientConfigData) {
        this.webClientBuilder = webClientBuilder;
        this.webClientConfigData = webClientConfigData;
    }

    public TutorResponse getTutorByUserId(String userId) {
        return getWebClient(userId).bodyToMono(TutorResponse.class).block();
    }

    private WebClient.ResponseSpec getWebClient(String userId) {
        return webClientBuilder.build()
                .get()
                .uri(TUTOR_URL, uriBuilder -> uriBuilder.pathSegment(userId).build())
                .header("X-User-ID", userId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(RuntimeException::new)
                );
    }

}
