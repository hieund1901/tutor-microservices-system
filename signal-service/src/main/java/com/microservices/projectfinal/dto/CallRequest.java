package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("call_request")
public class CallRequest {
    @JsonProperty("from_user_id")
    private String fromUserId;
    @JsonProperty("to_user_id")
    private String toUserId;
    @JsonProperty("sdp_offer")
    private SpdOffer sdpOffer;

    @Data
    public class SpdOffer {
        private String type;
        private String sdp;
    }
}
