package com.microservices.projectfinal.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;


public class MinioClientBuilder {
    @Value("${minio.url}")
    private String url;
    @Value("${minio.access-key}")
    private String accessKey;
    @Value("${minio.secret-key}")
    private String secretKey;


    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
