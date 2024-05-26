package com.microservices.projectfinal.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("availability_schedules")
@Builder
@Data
public class AvailabilitySchedulesCreateDTO {
    @JsonProperty("availabilities")
    private List<AvailabilityScheduleCreateDTO> availabilitySchedules;
}
