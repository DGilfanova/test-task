package com.technokratos.adboard.controller;

import java.util.Collections;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.technokratos.adboard.constant.Constant.BEARER;
import static com.technokratos.adboard.constant.TestConstant.FIRST_CHAT_ID;
import static com.technokratos.adboard.constant.TestConstant.SECOND_CHAT_ID;
import static com.technokratos.adboard.constant.TestConstant.FIRST_USER;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
@Sql(scripts = "/db/chat_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean_chat_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ChatApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void get_user_chat_count_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/chat")
            .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole())))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void get_chat_messages_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/chat/" + FIRST_CHAT_ID + "/message")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole())))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void get_400_when_get_messages_not_from_own_chat() throws Exception {
        mockMvc.perform(get("/api/v1/chat/" + SECOND_CHAT_ID + "/message")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole())))))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void get_chat_new_messages_count_successfully() throws Exception {
        mockMvc.perform(get("/api/v1/chat/" + FIRST_CHAT_ID + "/message/count")
                .header(AUTHORIZATION, BEARER.concat(jwtTokenProvider.generateAccessToken(
                    FIRST_USER.getEmail(), Collections.singletonMap("ROLE", FIRST_USER.getRole())))))
            .andExpect(status().isOk())
            .andExpect(content().string("2"));
    }
}
