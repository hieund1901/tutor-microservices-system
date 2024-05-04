package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;

import java.util.Date;

@JsonRootName("course_video")
@Builder
public class CourseVideoResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private boolean status;
    private long duration;
    private int numberOfOrder;
    private String thumbnailUrl;
    private Date createdAt;
    private Date modifiedAt;
}
