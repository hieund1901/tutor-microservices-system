package com.microservices.projectfinal.util;

import com.microservices.projectfinal.model.VideoInformationModel;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@ConditionalOnProperty(value = "storage.minio.enable", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
@Component
public class MediaFileUtils {
    private final MinioClient minioClient;

//    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "\\local-storage-media";
    public static final String IMAGE_BUCKET = "images";
    public static final String VIDEO_BUCKET = "videos";

    @SneakyThrows
    public String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        String extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        String imageName = UUID.randomUUID() + "." + extension;
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(IMAGE_BUCKET).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(IMAGE_BUCKET).build());
        }
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(IMAGE_BUCKET)
                        .object(imageName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );

        return imageName;
    }

    @SneakyThrows
    public VideoInformationModel saveVideo(MultipartFile video) {
        if (video == null || video.isEmpty()) return null;
        String extension = Objects.requireNonNull(video.getOriginalFilename()).split("\\.")[1];
        String videoName = UUID.randomUUID() + "." + extension;
        Path path = Paths.get(VIDEO_BUCKET);
        if (!Files.exists(path)) Files.createDirectories(path);
        Path fileNameAndPath = Paths.get(VIDEO_BUCKET, videoName);
        Files.write(fileNameAndPath, video.getBytes());
        long duration = getVideoDuration(fileNameAndPath.toString());
        Files.delete(fileNameAndPath);
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(VIDEO_BUCKET).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(VIDEO_BUCKET).build());
        }
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(VIDEO_BUCKET)
                        .object(videoName)
                        .stream(video.getInputStream(), video.getSize(), -1)
                        .build()
        );
        return VideoInformationModel.builder()
                .duration(duration)
                .title(videoName)
                .path(videoName)
                .build();

    }

    @SneakyThrows
    public InputStream getVideo(String videoName) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(VIDEO_BUCKET)
                        .object(videoName)
                        .build()
        );
    }

    @SneakyThrows
    private static long getVideoDuration(String fileName) {
        /*link download ffmpeg: https://www.gyan.dev/ffmpeg/builds/packages/ffmpeg-6.1.1-full_build.7z*/
        // Sử dụng FFprobe để lấy thông tin về video
        FFprobe ffprobe = new FFprobe("C:\\ffmpeg-6.1.1-full_build\\bin\\ffprobe.exe");
        FFmpegProbeResult probeResult = ffprobe.probe(fileName);
        // Lấy thời lượng video
        return (long) probeResult.getFormat().duration;
    }
}
