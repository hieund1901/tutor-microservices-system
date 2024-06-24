package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@JsonRootName("availability_schedule")
@Builder
@Data
public class AvailabilityScheduleCreateDTO {
    private Long id;

    @JsonProperty("time_key")
    @NotNull
    private Long timeKey;

    @JsonProperty("is_available")
    @Builder.Default
    private boolean isAvailable = true;
}
