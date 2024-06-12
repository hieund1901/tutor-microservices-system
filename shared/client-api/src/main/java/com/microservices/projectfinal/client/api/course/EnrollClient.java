package com.microservices.projectfinal.client.api.course;

import com.microservices.projectfinal.dto.CourseEnrollmentDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EnrollClient {
    private static final String ENROLL_URL = "/courseapi/enrollment";

    private final WebClient.Builder webClientBuilder;

    public EnrollClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public CourseEnrollmentDTO getEnrolledCourses(Long id) {
        return getWebClient(id).bodyToMono(CourseEnrollmentDTO.class).block();
    }

    private WebClient.ResponseSpec getWebClient(Long id) {
        return webClientBuilder.build()
                .get()
                .uri(ENROLL_URL + "/get-by-id", uriBuilder -> uriBuilder.pathSegment(id.toString()).build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(RuntimeException::new)
                );
    }
}
