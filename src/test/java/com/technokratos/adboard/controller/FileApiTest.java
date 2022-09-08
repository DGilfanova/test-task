package com.technokratos.adboard.controller;

import java.util.Collections;
import java.util.Objects;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import com.technokratos.adboard.dto.enums.FileType;
import com.technokratos.adboard.model.File;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import com.technokratos.adboard.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import static com.technokratos.adboard.constant.Constant.BEARER;
import static com.technokratos.adboard.constant.TestConstant.FIRST_USER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author d.gilfanova
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.yml"})
@ContextConfiguration(initializers = {
    TestPostgresContainer.PropertiesInitializer.class,
    TestMinioContainer.PropertiesInitializer.class})
@Sql(scripts = "/db/user_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean_user_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
 class FileApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private FileService fileService;

    @Test
     void file_download_successfully() throws Exception {
        byte[] image = IOUtils.toByteArray(Objects.requireNonNull(getClass().getClassLoader()
            .getResourceAsStream("files/test-image.jpg")));

        MockMultipartFile photo = new MockMultipartFile("photos", "test-image.jpg",
            MediaType.IMAGE_JPEG_VALUE, image);

        File file = fileService.uploadFile(photo, FileType.PHOTO);

        mockMvc.perform(get("/api/v1/file/" + file.getId())
            .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole()))))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, Objects.requireNonNull(photo.getContentType())))
            .andExpect(header().string(CONTENT_LENGTH, String.valueOf(photo.getSize())))
            .andExpect(header().string(CONTENT_DISPOSITION,
                "filename=\"" + photo.getOriginalFilename() + "\""));
    }
}
