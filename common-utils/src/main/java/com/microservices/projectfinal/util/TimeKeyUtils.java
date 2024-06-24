package com.microservices.projectfinal.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeKeyUtils {
    public static Long generateTimeKey(Instant dayStart, LocalTime startTime) {
        LocalDateTime localDateTime = LocalDateTime.of(dayStart.atZone(ZoneId.systemDefault()).toLocalDate(), startTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        return Long.parseLong(localDateTime.format(formatter));
    }

    public  static Long startTimeKey(Long timeKey) {
        String timeKeyString = timeKey.toString();
        String startTimeKey = timeKeyString.concat("00");
        return Long.parseLong(startTimeKey);
    }

    public static Long endTimeKey(Long timeKey) {
        String timeKeyString = timeKey.toString();
        String endTimeKey = timeKeyString.concat("23");
        return Long.parseLong(endTimeKey);
    }
}
