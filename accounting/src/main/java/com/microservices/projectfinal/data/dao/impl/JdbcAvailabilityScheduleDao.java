package com.microservices.projectfinal.data.dao.impl;

import com.microservices.projectfinal.data.dao.AvailabilityScheduleDao;
import com.microservices.projectfinal.data.model.GetAvailableRequest;
import com.microservices.projectfinal.data.sql.AvailabilityScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JdbcAvailabilityScheduleDao implements AvailabilityScheduleDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getAvailabilitySchedule(GetAvailableRequest availableRequest) {
        var sql = AvailabilityScheduleService.INSTANCE.getAvailabilitySchedule();
        try {
            return jdbcTemplate.queryForList(sql, Map.of(
                    "tutorId", availableRequest.getTutorId(),
                    "timeKey", availableRequest.getDimTimeKey()
            ));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
