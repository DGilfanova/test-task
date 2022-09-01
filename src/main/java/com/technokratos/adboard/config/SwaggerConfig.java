package com.technokratos.adboard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author d.gilfanova
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("AdBoard API")
                    .description("This is a sample Spring Boot RESTful service")
                    .version("1.0.0")
                    .contact(
                        new Contact()
                            .name("Diana Gilfanova")
                            .email("gilfanova.dianka@mail.ru")));
    }
}
