package com.microservices.projectfinal.service;

public interface IChatSessionRedisService {
    void regisCallSession(String sessionId, String fromUserId, String toUserId);

}
