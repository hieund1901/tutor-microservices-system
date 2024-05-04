package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "course_videos")
public class CourseVideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;
    @Setter
    @Column(name = "video_url", nullable = false)
    private String videoUrl;
    @Setter
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Setter
    @Column(nullable = false)
    private Long duration;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @Builder.Default
    @Column(name = "number_of_order", columnDefinition = "int default 0")
    private Integer numberOfOrder = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    // getters and setters
}
