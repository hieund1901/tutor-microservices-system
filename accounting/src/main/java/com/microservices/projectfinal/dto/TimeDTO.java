package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@JsonRootName("time")
@Builder
@Getter
public class TimeDTO {
    private Integer hourOfDay;
    private String timeString;
    private String dayName;
    private Integer dayOfWeek;
    private Integer weekOfYear;
    private Integer month;
    private String monthName;
    private Integer quarter;
    private Integer year;
}
