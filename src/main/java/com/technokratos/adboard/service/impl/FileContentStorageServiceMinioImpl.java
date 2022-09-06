package com.technokratos.adboard.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.technokratos.adboard.exception.FileStorageException;
import com.technokratos.adboard.model.FileContent;
import com.technokratos.adboard.service.FileContentStorageService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.technokratos.adboard.constant.Constant.MINIO_BUCKET_NAME;
import static com.technokratos.adboard.constant.Constant.MINIO_DEFAULT_OBJECT_SIZE;
import static com.technokratos.adboard.constant.Constant.MINIO_DEFAULT_PART_SIZE;

/**
 * @author d.gilfanova
 */
@Slf4j
@Component
public class FileContentStorageServiceMinioImpl implements FileContentStorageService {

    private final MinioClient minioClient;

    public FileContentStorageServiceMinioImpl(MinioClient minioClient) {
        this.minioClient = minioClient;

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(MINIO_BUCKET_NAME)
                .build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MINIO_BUCKET_NAME).build());
            }
        }
        catch (Exception e) {
            log.error("Error when creating bucket {}", MINIO_BUCKET_NAME, e);

            throw new FileStorageException("Minio error during create bucket "
                                           + MINIO_BUCKET_NAME, e);
        }
    }

    @Override
    @Transactional
    public String saveFileContent(InputStream file, String fileName) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String objectName = sdf.format(new Date()) + "/" + UUID.randomUUID() + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                .object(objectName)
                .stream(file, MINIO_DEFAULT_OBJECT_SIZE, MINIO_DEFAULT_PART_SIZE)
                .bucket(MINIO_BUCKET_NAME)
                .build());

            return objectName;
        } catch (Exception e) {
            log.error("Error with uploading file with name {} to minio storage", fileName, e);

            throw new FileStorageException("Minio error during upload file " + fileName, e);
        }
    }

    @Override
    @Transactional
    public FileContent getFileContent(String id) {
        try {
            return FileContent.builder()
                .id(id)
                .content(minioClient.getObject(GetObjectArgs.builder()
                    .bucket(MINIO_BUCKET_NAME)
                    .object(id)
                    .build()))
                .build();
        } catch (Exception e) {
            log.error("Error with downloading file with id {} from minio storage", id, e);

            throw new FileStorageException("Minio error during download file with id " + id, e);
        }
    }
}
