package com.microservices.projectfinal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CallSession {
    private String fromUserId;
    private String toUserId;
    private CallRequest.SpdOffer callerSdpOffer;
    private CallRequest.SpdOffer calleeSdpAnswer;
    @Builder.Default
    private CallStatus status = CallStatus.RINGING;

    enum CallStatus {
        RINGING,
        CONNECTED,
        ENDED
    }
}
