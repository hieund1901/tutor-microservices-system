package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonRootName("availabilities_booked")
@Getter
@Builder
public class AvailabilitiesRegisterResponseDTO {
    private List<AvailabilitiesResponseDTO.AvailabilityResponseDTO> availabilities;
    private Long transactionId;
}
