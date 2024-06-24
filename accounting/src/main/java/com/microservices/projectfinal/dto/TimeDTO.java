package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;

@JsonRootName("time")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TimeDTO {
    @JsonProperty("hour_of_day")
    private Integer hourOfDay;
    @JsonProperty("time_string")
    private String timeString;
    @JsonProperty("day_name")
    private String dayName;
    @JsonProperty("day_of_week")
    private Integer dayOfWeek;
    @JsonProperty("day_of_month")
    private Integer dayOfMonth;
    @JsonProperty("week_of_year")
    private Integer weekOfYear;
    @JsonProperty("_month")
    private Integer month;
    @JsonProperty("month_name")
    private String monthName;
    @JsonProperty("quarter")
    private Integer quarter;
    @JsonProperty("_year")
    private Integer year;
}
