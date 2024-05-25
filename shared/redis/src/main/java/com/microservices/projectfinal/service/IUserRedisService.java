package com.microservices.projectfinal.service;


public interface IUserRedisService {
    void setUserStatus(String userId, String status);
    String getUserStatus(String userId);
}
