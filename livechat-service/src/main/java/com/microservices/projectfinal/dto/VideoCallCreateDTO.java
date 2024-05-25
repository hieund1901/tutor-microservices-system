package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonRootName("video_call")
public class VideoCallCreateDTO {
    @NotEmpty
    @NotNull
    private String calleeId;
    private boolean recorded;
}
