package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "dim_time")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DimTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_key", nullable = false)
    private Long timeKey;

    @Column(name = "hour_of_day", nullable = false)
    private Integer hourOfDay;

    @Column(name = "time_string", nullable = false)
    private LocalTime timeString;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "day_name", nullable = false)
    private String dayName;

    @Column(name = "_date", nullable = false)
    private LocalDate date;

    @Column(name = "week_of_year", nullable = false)
    private Integer weekOfYear;

    @Column(name = "_month", nullable = false)
    private Integer month;

    @Column(name = "month_name", nullable = false)
    private String monthName;

    @Column(name = "quarter", nullable = false)
    private Integer quarter;

    @Column(name = "_year", nullable = false)
    private Integer year;
}
