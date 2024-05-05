package com.microservices.projectfinal.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@JsonRootName("course_video")
@Builder
@Getter
public class CourseVideoUpdateDTO {
    private String title;
    private String description;
    private String videoUrl;
    private int numberOfOrder;
    private String thumbnailUrl;
    private MultipartFile newVideo;
    private MultipartFile newThumbnail;
    private Date createdAt;
    private Date modifiedAt;

}
