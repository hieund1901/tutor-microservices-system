package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonRootName("appointment")
@Builder
@Getter
public class AppointmentResponseDTO {
    private List<AvailabilitiesResponseDTO.AvailabilityResponseDTO> availabilities;
    private long total;
    private int page;
    private int size;
    private int totalPages;
}
