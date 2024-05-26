package com.microservices.projectfinal.handler;

import com.microservices.projectfinal.constant.CommonConstant;
import com.microservices.projectfinal.dto.*;
import com.microservices.projectfinal.service.IUserRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Controller
public class SignalingHandler {

    private final SimpMessagingTemplate simpleMessageTemplate;
    private final IUserRedisService userService;
    private ConcurrentHashMap<String, String> userSessionMap = new ConcurrentHashMap<>();

    @MessageMapping("/register")
    public void handleRegister(Register register, @Header("simpSessionId") String sessionId) {
        userSessionMap.put(register.getUserId(), sessionId);
        userService.setUserStatus(register.getUserId(), CommonConstant.UserStatus.ONLINE.name());
    }


    @MessageMapping("/call")
    public void handleCall(CallRequest callRequest) {
        //check online for toUserId
        var toUserStatus = userService.getUserStatus(callRequest.getToUserId());

        if (toUserStatus.equals(CommonConstant.UserStatus.BUSY.name())) {
            log.error("User is busy: {}", callRequest.getToUserId());
            return;
        }

        var onlineUserSessionId = userSessionMap.get(callRequest.getToUserId());
        if (onlineUserSessionId == null || toUserStatus.equals(CommonConstant.UserStatus.OFFLINE.name())) {
            log.error("User is not online: {}", callRequest.getToUserId());
            return;
        }
        log.info(onlineUserSessionId);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(onlineUserSessionId);
        headerAccessor.setLeaveMutable(true);
        simpleMessageTemplate.convertAndSendToUser(onlineUserSessionId,
                "/queue/incoming-call",
                new IncomingCallRequest(onlineUserSessionId, callRequest.getFromUserId(), callRequest.getToUserId(), callRequest.getSdpOffer()),
                headerAccessor.getMessageHeaders());
    }

    @MessageMapping("/call-accept")
    public void handleAccept(CallAcceptRequest request) {
        var toUserStatus = userService.getUserStatus(request.getToUserId());

        if (toUserStatus.equals(CommonConstant.UserStatus.BUSY.name())) {
            log.error("User is busy: {}", request.getToUserId());
            return;
        }

        var onlineUserSessionId = userSessionMap.get(request.getToUserId());
        if (onlineUserSessionId == null || toUserStatus.equals(CommonConstant.UserStatus.OFFLINE.name())) {
            log.error("User is not online: {}", request.getToUserId());
            return;
        }

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(onlineUserSessionId);
        headerAccessor.setLeaveMutable(true);

        simpleMessageTemplate.convertAndSendToUser(onlineUserSessionId,
                "/queue/call-accepted",
                request, headerAccessor.getMessageHeaders());
    }
}
