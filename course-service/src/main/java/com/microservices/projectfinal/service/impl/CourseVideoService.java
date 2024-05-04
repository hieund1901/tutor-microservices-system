package com.microservices.projectfinal.service.impl;

import com.microservices.projectfinal.dto.CourseVideoCreateDTO;
import com.microservices.projectfinal.dto.CourseVideoResponseDTO;
import com.microservices.projectfinal.entity.CourseEntity;
import com.microservices.projectfinal.entity.CourseVideoEntity;
import com.microservices.projectfinal.model.VideoInformationModel;
import com.microservices.projectfinal.repository.CourseRepository;
import com.microservices.projectfinal.repository.CourseVideoRepository;
import com.microservices.projectfinal.service.ICourseVideoService;
import com.microservices.projectfinal.util.MediaFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseVideoService implements ICourseVideoService {

    private final CourseVideoRepository courseVideoRepository;
    private final CourseRepository courseRepository;
    private final ResourceLoader resourceLoader;

    @Transactional
    @Override
    public void createCourseVideo(CourseVideoCreateDTO courseVideoCreateDTO) {
        CourseEntity courseEntity = courseRepository.findById(courseVideoCreateDTO.getCourseId()).orElse(null);
        //chưa handle thứ tự video
        CourseVideoEntity courseVideoEntity = CourseVideoEntity.builder()
                .title(courseVideoCreateDTO.getTitle())
                .course(courseEntity)
                .description(courseVideoCreateDTO.getDescription())
                .build();

        CourseVideoEntity saved = courseVideoRepository.save(courseVideoEntity);

        String thumbnailPath = MediaFileUtils.saveImage(courseVideoCreateDTO.getThumbnail());
        VideoInformationModel videoInfor = MediaFileUtils.saveVideo(courseVideoCreateDTO.getVideo());

        saved.setThumbnailUrl(thumbnailPath);
        saved.setVideoUrl(videoInfor.getPath());
        saved.setDuration(videoInfor.getDuration());

        courseVideoRepository.save(saved);
    }

    @Override
    public List<CourseVideoResponseDTO> getCourseVideoByCourseId(Long courseId) {
        return List.of();
    }

    @Override
    public CourseVideoResponseDTO getCourseVideoById(Long courseId, Long courseVideoId) {
        return null;
    }

    @Override
    public Mono<Resource> getVideoResource(Long courseId, Long courseVideoId) {
        CourseVideoEntity courseVideoEntity = courseVideoRepository.findByCourseIdAndId(courseId, courseVideoId);
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + courseVideoEntity.getVideoUrl()));
    }
}
