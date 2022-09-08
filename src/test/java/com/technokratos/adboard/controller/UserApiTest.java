package com.technokratos.adboard.controller;

import java.util.Collections;
import java.util.Objects;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
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
import static com.technokratos.adboard.constant.TestConstant.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
@Sql(scripts = "/db/all_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean_all_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
 class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
     void create_ad_successfully() throws Exception {
        byte[] image = IOUtils.toByteArray(Objects.requireNonNull(getClass().getClassLoader()
            .getResourceAsStream("files/test-image.jpg")));

        MockMultipartFile photo = new MockMultipartFile("photos", "test-image.jpg",
            MediaType.IMAGE_JPEG_VALUE, image);

        mockMvc.perform(multipart("/api/v1/user/ad")
                .file(photo)
                .param("title", NEW_AD_TITLE)
                .param("content", NEW_AD_CONTENT)
                .param("isActive", ACTIVE.toString())
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole())))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.title", is(NEW_AD_TITLE)))
            .andExpect(jsonPath("$.content", is(NEW_AD_CONTENT)))
            .andExpect(jsonPath("$.isActive", is(ACTIVE)))
            .andExpect(jsonPath("$.user.id", is(FIRST_USER_ID)))
            .andExpect(jsonPath("$.photos[0].name", is("test-image.jpg")));
    }

    @Test
     void update_ad_status_successfully() throws Exception {
        mockMvc.perform(put("/api/v1/user/ad/" + SECOND_AD_ID + "/status")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"isActive\": \"" + NOT_ACTIVE + "\" \n" +
                         "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isActive", is(NOT_ACTIVE)));
    }

    @Test
     void get_400_when_update_status_of_non_own_ad() throws Exception {
        mockMvc.perform(put("/api/v1/user/ad/" + FIRST_AD_ID + "/status")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"isActive\": \"" + NOT_ACTIVE + "\" \n" +
                         "}"))
            .andExpect(status().isBadRequest());
    }

    @Test
     void make_deal_successfully() throws Exception {
        mockMvc.perform(put("/api/v1/user/deal/" + FIRST_DEAL_ID + "/status")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    SECOND_USER_EMAIL
                    , Collections.singletonMap("ROLE", FIRST_USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isCompleted", is(true)))
            .andExpect(jsonPath("$.advertisement.isActive", is(false)));
    }

    @Test
     void get_400_when_make_not_own_deal() throws Exception {
        mockMvc.perform(put("/api/v1/user/deal/" + SECOND_DEAL_ID + "/status")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isBadRequest());
    }
}
