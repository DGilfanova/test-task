package com.technokratos.adboard.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @author d.gilfanova
 */
public class TestPostgresContainer {

    private static final String IMAGE_VERSION = "postgres:13";
    public static PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>(IMAGE_VERSION);

        container.start();
    }

    public static class PropertiesInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                .of("spring.datasource.driver-class-name=" + container.getDriverClassName(),
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword())
                .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
