package com.technokratos.adboard.controller;

import com.technokratos.adboard.container.TestMinioContainer;
import com.technokratos.adboard.container.TestPostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.technokratos.adboard.constant.TestConstant.NEW_USER_EMAIL;
import static com.technokratos.adboard.constant.TestConstant.REPEAT_USER_EMAIL;
import static org.hamcrest.Matchers.*;
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
public class RegistrationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_user_successfully() throws Exception {
        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"email\": \"" + NEW_USER_EMAIL + "\", \n" +
                         "\"password\": \"Abc123+-\", \n" +
                         "\"role\": \"USER\" \n" +
                         "}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.email", is(NEW_USER_EMAIL)));
    }

    @Test
    public void get_400_when_create_user_with_weak_password() throws Exception {
        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"email\": \"" + NEW_USER_EMAIL + "\", \n" +
                         "\"password\": \"123\", \n" +
                         "\"role\": \"USER\" \n" +
                         "}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void get_400_when_create_user_with_existing_email() throws Exception {
        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                         "\"email\": \"" + REPEAT_USER_EMAIL + "\", \n" +
                         "\"password\": \"123\", \n" +
                         "\"role\": \"USER\" \n" +
                         "}"))
            .andExpect(status().isBadRequest());
    }
}
