package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@JsonRootName("course_video")
@Builder
@Getter
public class CourseVideoCreateDTO {
    @NotNull
    @JsonProperty("course_id")
    private Long courseId;
    @NotNull
    private MultipartFile video;
    private MultipartFile thumbnail;
    @NotEmpty
    private String title;
    private String description;
}
