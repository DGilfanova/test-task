package com.technokratos.adboard.controller;

import java.util.Collections;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.technokratos.adboard.constant.Constant.BEARER;
import static com.technokratos.adboard.constant.TestConstant.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author d.gilfanova
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.yml"})
@ContextConfiguration(initializers = {TestPostgresContainer.PropertiesInitializer.class,
    TestMinioContainer.PropertiesInitializer.class})
@Sql(scripts = "/db/test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdvertisementApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void get_ad_count_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \n" +
                     "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void get_filtered_ad_by_date_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"dateAfter\": \"" + TIMESTAMP + "\"\n" +
                         "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(AD_ID)));
    }

    @Test
    public void get_filtered_ad_by_user_email_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"email\": \"user1@\"\n" +
                         "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(AD_ID)));
    }

    @Test
    public void get_filtered_ad_by_min_photo_count_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"minPhotoCount\": 1\n" +
                         "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(AD_ID)));
    }

    @Test
    public void get_ad_by_id_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/advertisement/" + AD_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is(SPECIAL_AD_TITLE)));
    }

    @Test
    public void get_404_when_get_non_exist_ad_() throws Exception {
        mockMvc.perform(get("/api/v1/advertisement/" + NON_EXIST_AD_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    public void create_deal_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement/" + AD_ID + "/deal")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    USER.getEmail(), Collections.singletonMap("ROLE", USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void get_403_when_create_deal_without_token() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement/" + AD_ID + "/deal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isForbidden());
    }

    @Test
    public void get_401_when_create_deal_with_invalid_token() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement/" + AD_ID + "/deal")
                .header(AUTHORIZATION, jwtTokenProvider.generateAccessToken(
                    USER.getEmail(), Collections.singletonMap("ROLE", USER.getRole())))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void get_400_when_create_deal_with_yourself() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement/" + USER_AD_ID + "/deal")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    USER.getEmail(), Collections.singletonMap("ROLE", USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void get_400_when_create_already_existed_deal() throws Exception {
        mockMvc.perform(post("/api/v1/advertisement/" + DEAL_AD_ID + "/deal")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    USER.getEmail(), Collections.singletonMap("ROLE", USER.getRole()))))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "}"))
            .andExpect(status().isBadRequest());
    }
}
