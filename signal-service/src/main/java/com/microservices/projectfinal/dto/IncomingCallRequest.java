package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonRootName("incoming_call_request")
@AllArgsConstructor
@Getter
public class IncomingCallRequest {
    private String sessionId;
    private String fromUserId;
    private String toUserId;
    private CallRequest.SpdOffer callerSdpOffer;
}
