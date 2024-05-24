package com.microservices.projectfinal.handler;

import com.microservices.projectfinal.dto.CallAcceptRequest;
import com.microservices.projectfinal.dto.CallSession;
import com.microservices.projectfinal.dto.IncomingCallRequest;
import com.microservices.projectfinal.dto.CallRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Controller
public class SignalingHandler {

    private final SimpMessagingTemplate simpleMessageTemplate;
    private ConcurrentHashMap<String, CallSession> callSessions = new ConcurrentHashMap<>();


    @MessageMapping("/call")
    public void handleCall(CallRequest callRequest) {
        //check online for toUserId

        String sessionId = UUID.randomUUID().toString();
        CallSession callSession = CallSession.builder()
                .sessionId(sessionId)
                .fromUserId(callRequest.getFromUserId())
                .toUserId(callRequest.getToUserId())
                .callerSdpOffer(callRequest.getSdpOffer())
                .build();

        callSessions.put(sessionId, callSession);

        simpleMessageTemplate.convertAndSendToUser(callRequest.getToUserId(),
                "/topic/incoming-call",
                new IncomingCallRequest(sessionId, callRequest.getFromUserId(), callRequest.getToUserId(), callRequest.getSdpOffer()));
    }

    @MessageMapping("/accept")
    public void handleAccept(CallAcceptRequest request) {
        CallSession session = callSessions.get(request.getSessionId());
        if (session == null) {
            log.error("Session not found for sessionId: {}", request.getSessionId());
            return;
        }

        session.setCalleeSdpAnswer(request.getCalleeSdpAnswer());
        simpleMessageTemplate.convertAndSendToUser(session.getFromUserId(),
                "/topic/call-accepted",
                new IncomingCallRequest(session.getSessionId(), session.getFromUserId(), session.getToUserId(), session.getCalleeSdpAnswer()));
    }
}
