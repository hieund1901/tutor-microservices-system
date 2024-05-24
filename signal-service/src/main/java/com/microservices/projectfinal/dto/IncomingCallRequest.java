package com.microservices.projectfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IncomingCallRequest {
    private String sessionId;
    private String fromUserId;
    private String toUserId;
    private CallRequest.SpdOffer callerSdpOffer;
}
