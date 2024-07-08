package com.microservices.projectfinal.handler;

import com.microservices.projectfinal.constant.CommonConstant;
import com.microservices.projectfinal.dto.CallAcceptRequest;
import com.microservices.projectfinal.dto.CallRequest;
import com.microservices.projectfinal.dto.IncomingCallRequest;
import com.microservices.projectfinal.dto.Register;
import com.microservices.projectfinal.service.IUserRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
        userSessionMap.put(register.getUserId(), register.getUserId());
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
        simpleMessageTemplate.convertAndSend(
                "/queue/incoming-call/" + onlineUserSessionId,
                new IncomingCallRequest(onlineUserSessionId, callRequest.getFromUserId(), callRequest.getToUserId(), callRequest.getSdpOffer())
        );
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

        simpleMessageTemplate.convertAndSend(
                "/queue/call-accepted/" + onlineUserSessionId,
                request);
    }

//    @MessageMapping("ice-candidate")
//    public void handleIceCandidate(IceCandidateRequest request) {
//        var toUserStatus = userService.getUserStatus(request.getToUserId());
//
//        if (toUserStatus.equals(CommonConstant.UserStatus.BUSY.name())) {
//            log.error("User is busy: {}", request.getToUserId());
//            return;
//        }
//
//        var onlineUserSessionId = userSessionMap.get(request.getToUserId());
//        if (onlineUserSessionId == null || toUserStatus.equals(CommonConstant.UserStatus.OFFLINE.name())) {
//            log.error("User is not online: {}", request.getToUserId());
//            return;
//        }
//
//        simpleMessageTemplate.convertAndSend(
//                "/queue/ice-candidate/" + onlineUserSessionId,
//                request);
//    }
}
