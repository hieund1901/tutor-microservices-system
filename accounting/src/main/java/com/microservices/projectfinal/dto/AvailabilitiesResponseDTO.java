package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.microservices.projectfinal.entity.AvailabilityEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonRootName("availabilities")
public class AvailabilitiesResponseDTO {
    private List<AvailabilityResponseDTO> availabilities;

    @Builder
    @Getter
    public static class AvailabilityResponseDTO {
        private Long id;
        private AvailabilityEntity.Status status;
        private boolean isAvailable;
        private TutorResponseDTO tutor;
        private TimeDTO time;
    }
}