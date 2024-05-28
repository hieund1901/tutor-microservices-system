package com.microservices.projectfinal.data.dao;

import com.microservices.projectfinal.data.model.GetAvailableRequest;

import java.util.List;
import java.util.Map;

public interface AvailabilityScheduleDao {
    List<Map<String, Object>> getAvailabilitySchedule(GetAvailableRequest availableRequest);
}
