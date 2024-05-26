package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalTime;

@JsonRootName("availability_schedule")
@Builder
@Data
public class AvailabilityScheduleCreateDTO {
    private Long id;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private Instant dayStart;

    @Builder.Default
    private boolean isAvailable = true;
}
