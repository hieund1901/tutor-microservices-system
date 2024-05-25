package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.service.IUserRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRedisService implements IUserRedisService {
    private static final String USER_STATUS_KEY = "USER_STATUS:%s";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setUserStatus(String userId, String status) {
        String key = String.format(USER_STATUS_KEY, userId);
        redisTemplate.opsForValue().set(key, status);
    }

    @Override
    public String getUserStatus(String userId) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(String.format(USER_STATUS_KEY, userId))).toString();
    }
}
