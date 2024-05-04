package com.microservices.projectfinal.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Builder
@Getter
public class CourseCreateDTO {
    private String name;
    private String description;
    private String subject;
    private BigDecimal price;
    private MultipartFile thumbnail;
}
