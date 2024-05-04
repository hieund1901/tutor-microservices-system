package com.microservices.projectfinal.client.api.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.projectfinal.config.WebclientConfigData;
import com.microservices.projectfinal.dto.TutorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TutorClient {
    private static final String TUTOR_URL = "/accounting/tutor";
    private final WebClient.Builder webClientBuilder;
    private final WebclientConfigData webClientConfigData;

    public TutorClient(WebClient.Builder webClientBuilder, WebclientConfigData webClientConfigData) {
        this.webClientBuilder = webClientBuilder;
        this.webClientConfigData = webClientConfigData;
    }

    public TutorResponse getTutorByEmail(String email) {
        return getWebClient(email).bodyToMono(TutorResponse.class).block();
    }

    private WebClient.ResponseSpec getWebClient(String email) {
        return webClientBuilder.build()
                .get()
                .uri(TUTOR_URL + "/by-email", uriBuilder -> uriBuilder.queryParam("email", email).build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(RuntimeException::new)
                );
    }

    public TutorResponse getTutorByAccountId(Long accountId) {
        return getWebClient(accountId).bodyToMono(TutorResponse.class).block();
    }

    private WebClient.ResponseSpec getWebClient(Long accountId) {
        return webClientBuilder.build()
                .get()
                .uri(TUTOR_URL + "/by-account-id", uriBuilder -> uriBuilder.queryParam("accountId", accountId).build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(RuntimeException::new)
                );
    }
}
