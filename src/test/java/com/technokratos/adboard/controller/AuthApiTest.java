package com.technokratos.adboard.controller;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.model.RefreshToken;
import com.technokratos.adboard.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.technokratos.adboard.constant.TestConstant.EXPIRED_REFRESH_TOKEN;
import static com.technokratos.adboard.constant.TestConstant.INVALID_REFRESH_TOKEN;
import static com.technokratos.adboard.constant.TestConstant.NEW_USER_EMAIL;
import static com.technokratos.adboard.constant.TestConstant.FIRST_USER;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author d.gilfanova
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.yml"})
@ContextConfiguration(initializers = {
    TestPostgresContainer.PropertiesInitializer.class,
    TestMinioContainer.PropertiesInitializer.class})
@Sql(scripts = "/db/user_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean_user_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    public void login_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"email\": \"" + FIRST_USER.getEmail() + "\", \n" +
                         "\"password\": \"" + FIRST_USER.getPassword() + "\" \n" +
                         "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken", notNullValue()))
            .andExpect(jsonPath("$.refreshToken", notNullValue()));
    }

    @Test
    public void get_401_when_login_with_invalid_credentionals() throws Exception {
        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"email\": \"" + NEW_USER_EMAIL + "\", \n" +
                         "\"password\": \"" + FIRST_USER.getPassword() + "\" \n" +
                         "}"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void refresh_token_successfully() throws Exception {
        RefreshToken refreshToken = jwtTokenService.addRefreshToken(UserResponse.builder()
            .id(FIRST_USER.getId())
            .email(FIRST_USER.getEmail())
            .role(FIRST_USER.getRole())
            .build());

        mockMvc.perform(post("/api/v1/token/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"refreshToken\": \"" + refreshToken.getToken() + "\" \n" +
                         "}"))
            .andExpect(status().isOk());
    }

    @Test
    public void get_401_when_refresh_invalid_token() throws Exception {
        mockMvc.perform(post("/api/v1/token/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"refreshToken\": \"" + INVALID_REFRESH_TOKEN + "\" \n" +
                         "}"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void get_401_when_refresh_expired_token() throws Exception {
        mockMvc.perform(post("/api/v1/token/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"refreshToken\": \"" + EXPIRED_REFRESH_TOKEN + "\" \n" +
                         "}"))
            .andExpect(status().isUnauthorized());
    }
}
