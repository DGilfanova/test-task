package com.technokratos.adboard.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;

/**
 * @author d.gilfanova
 */
public class TestMinioContainer {

    private static final String IMAGE_VERSION = "minio/minio";
    private static final String MINIO_ACCESS_KEY = "minioadmin";
    private static final String MINIO_SECRET_KEY = "minioadmin123";
    private static final Integer MINIO_PORT = 9000;

    public static final GenericContainer<?> minioContainer;

    static {
        minioContainer = new GenericContainer<>(IMAGE_VERSION)
            .withExposedPorts(MINIO_PORT)
            .withEnv("MINIO_ACCESS_KEY", MINIO_ACCESS_KEY)
            .withEnv("MINIO_SECRET_KEY", MINIO_SECRET_KEY)
            .withCommand("server /data");

        minioContainer.start();
    }

    public static class PropertiesInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                .of(String.format("minio.url=http://%s:%s",
                        minioContainer.getHost(), minioContainer.getFirstMappedPort()),
                    "minio.access-key=" + MINIO_ACCESS_KEY,
                    "minio.secret-key=" + MINIO_SECRET_KEY
                )
                .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
