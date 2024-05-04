package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonRootName("course")
@Builder
@Getter
public class CourseResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonProperty("thumbnail_path")
    private String thumbnailPath;
    private String status;
    private String subject;

    @JsonProperty("tutor")
    private TutorResponse tutor;

    @Builder.Default
    private List<CourseVideoResponseDTO> courseVideos = new ArrayList<>();

    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("modified_at")
    private Date modifiedAt;
}
