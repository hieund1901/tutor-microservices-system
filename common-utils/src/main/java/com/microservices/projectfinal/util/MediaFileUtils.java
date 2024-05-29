package com.microservices.projectfinal.util;

import com.microservices.projectfinal.model.VideoInformationModel;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class MediaFileUtils {

//    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "\\local-storage-media";
    private static final String IMAGE_BUCKET = "images";
    private static final String VIDEO_BUCKET = "videos";

    @SneakyThrows
    public static String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
//        String extension = file.getOriginalFilename().split("\\.")[1];
//        String imageName = UUID.randomUUID() + "." + extension;
//        Path path = Paths.get(IMAGE_DIR);
//        if (!Files.exists(path)) Files.createDirectories(path);
//
//        Path fileNameAndPath = Paths.get(IMAGE_DIR, imageName);
//        Files.write(fileNameAndPath, file.getBytes());
//        return fileNameAndPath.toString();
        MinioClient minioClient  = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("QWBfiGKTIxn8ADIIJoKj", "lAQkhF4vK8hH8avxiWlIu3JIhW4c5JGx1rKThDqZ")
                .build();
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(IMAGE_BUCKET).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(IMAGE_BUCKET).build());
        }
        String fileName = UUID.randomUUID() + file.getOriginalFilename();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(IMAGE_BUCKET)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );

        return fileName;
    }

    @SneakyThrows
    public static VideoInformationModel saveVideo(MultipartFile video) {
        if (video == null || video.isEmpty()) return null;
        String extension = video.getOriginalFilename().split("\\.")[1];
        String videoName = UUID.randomUUID() + "." + extension;
        Path path = Paths.get(VIDEO_BUCKET);
        if (!Files.exists(path)) Files.createDirectories(path);
        Path fileNameAndPath = Paths.get(VIDEO_BUCKET, videoName);
        Files.write(fileNameAndPath, video.getBytes());
        long duration = getVideoDuration(fileNameAndPath.toString());
        Files.delete(fileNameAndPath);
        MinioClient minioClient  = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("QWBfiGKTIxn8ADIIJoKj", "lAQkhF4vK8hH8avxiWlIu3JIhW4c5JGx1rKThDqZ")
                .build();
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
    private static long getVideoDuration(String fileName) {
        /*link download ffmpeg: https://www.gyan.dev/ffmpeg/builds/packages/ffmpeg-6.1.1-full_build.7z*/
        // Sử dụng FFprobe để lấy thông tin về video
        FFprobe ffprobe = new FFprobe("C:\\ffmpeg-6.1.1-full_build\\bin\\ffprobe.exe");
        FFmpegProbeResult probeResult = ffprobe.probe(fileName);
        // Lấy thời lượng video
        return (long) probeResult.getFormat().duration;
    }

//    @SneakyThrows
//    private void makeBucket(String bucketName) {
//        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//        if (!found) minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//    }
}
