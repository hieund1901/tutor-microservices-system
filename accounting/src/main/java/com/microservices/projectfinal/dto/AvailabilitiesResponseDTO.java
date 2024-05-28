package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonRootName("availabilities")
public class AvailabilitiesResponseDTO {
    @JsonUnwrapped
    private List<AvailabilityResponseDTO> availabilities;

    @Builder
    @Getter
    public static class AvailabilityResponseDTO {
        private Long id;
        private String tutorId;
        private TimeDTO time;
    }
}