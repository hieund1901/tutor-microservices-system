package com.microservices.projectfinal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfigData {
    private String url;
    private String accessKey;
    private String secretKey;
}
