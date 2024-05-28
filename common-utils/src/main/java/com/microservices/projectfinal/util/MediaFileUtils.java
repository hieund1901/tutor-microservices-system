package com.microservices.projectfinal.util;

import com.microservices.projectfinal.model.VideoInformationModel;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
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

    }

    @SneakyThrows
    public static VideoInformationModel saveVideo(MultipartFile video) {
        if (video == null || video.isEmpty()) return null;
        String extension = video.getOriginalFilename().split("\\.")[1];
        String videoName = UUID.randomUUID() + "." + extension;
        Path path = Paths.get(VIDEO_DIR);
        if (!Files.exists(path)) Files.createDirectories(path);
        Path fileNameAndPath = Paths.get(VIDEO_DIR, videoName);
        Files.write(fileNameAndPath, video.getBytes());
        long duration = getVideoDuration(fileNameAndPath.toString());
        return VideoInformationModel.builder()
                .duration(duration)
                .title(videoName)
                .path(fileNameAndPath.toString())
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

    @SneakyThrows
    private void makeBucket(String bucketName) {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }
}
