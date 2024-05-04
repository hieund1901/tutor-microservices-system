package com.microservices.projectfinal.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoInformationModel {
    private Long duration;
    private String title;
    private String path;
}
