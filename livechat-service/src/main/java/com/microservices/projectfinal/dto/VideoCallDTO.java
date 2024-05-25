package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@JsonRootName("video_call")
@Builder
@Getter
public class VideoCallDTO {
    private String callSessionId;
    private String callerId;
    private String calleeId;
}
