package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName("call_accept_request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CallAcceptRequest {
    private String toUserId;
    private CallRequest.SpdOffer calleeSdpAnswer;
}
