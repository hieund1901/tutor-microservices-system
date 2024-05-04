package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@JsonRootName("course")
@Builder
@Getter
public class CourseCreateDTO {
    private String name;
    private String description;
    private String subject;
    private BigDecimal price;
    private MultipartFile thumbnail;
}
