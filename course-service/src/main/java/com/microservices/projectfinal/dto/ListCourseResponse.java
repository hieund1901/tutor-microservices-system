package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@JsonRootName("courses")
@Builder
@Getter
public class ListCourseResponse {
    private int page;
    private int size;
    private int totalPage;
    private long totalElements;
    private Iterable<CourseResponseDTO> courses;
}
