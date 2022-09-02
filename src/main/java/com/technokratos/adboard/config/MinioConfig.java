package com.technokratos.adboard.config;

import com.technokratos.adboard.exception.FileStorageException;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author d.gilfanova
 */
@Configuration
public class MinioConfig {

    public final static long MINIO_DEFAULT_OBJECT_SIZE = -1;
    public final static long MINIO_DEFAULT_PART_SIZE = 10485760;
    public final static String MINIO_BUCKET_NAME = "adboardfile";

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Bean
    public MinioClient minioClient() {
        try {
            return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
        } catch (Exception e) {
            throw new FileStorageException("Error during creating minio client");
        }
    }
}
