package com.codeSharingPlatform;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.repositories.CodeRepository;
import com.codeSharingPlatform.services.CodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApiControllerTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.script.create.code_one}")
    private String sqlCreateFirstCode;

    @Value("${sql.script.create.code_two}")
    private String sqlCreateSecondCode;

    @Value("${sql.script.delete.code}")
    private String sqlDeleteCode;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlCreateFirstCode);
        jdbc.execute(sqlCreateSecondCode);
    }

    @Test
    public void getCodeHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/code/{id}",3))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", is("public static void ...")))
                .andExpect(jsonPath("$.date", is("2020/05/05 12:00:43:455")))
                .andExpect(jsonPath("$.time", is(0)))
                .andExpect(jsonPath("$.views", is(0)));
    }

    @Test
    public void getCodeHttpRequestInvalidIdNotFoundResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/code/{id}",1))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getCodeHttpRequestViewRestrictionNotFoundResponse() throws Exception {
        Code codeToUse = new Code();
        String time = codeToUse.formatDate(LocalDateTime.now());
        Code testViewRestriction = new Code("5","class Car { ...",time,8000L,1L,true,
                true);

        entityManager.persist(testViewRestriction);
        entityManager.flush();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/code/{id}","5"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/code/{id}","5"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getCodeHttpRequestTimeRestrictionNotFoundResponse() throws Exception {
        Code codeToUse = new Code();
        String time = codeToUse.formatDate(LocalDateTime.now());
        Code testTimeViewRestriction = new Code("6","public static void ...", time, 1L,2L,
                true,true);

        entityManager.persist(testTimeViewRestriction);
        entityManager.flush();

        Thread.sleep(1000);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/code/{id}","6"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getListOfLatestCodeHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/code/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void addCodeHttpRequest() throws Exception {
        Code code = new Code("1","Secret code","2022-09-21 12:20:47:105", 6000L, 5L,
                true,true);

        this.mockMvc.perform(post("/api/code/new").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(code)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void addCodeHttpRequestInvalidCodeBadRequestResponse() throws Exception {
        Code code = new Code();

        this.mockMvc.perform(post("/api/code/new").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(code)))
                .andExpect(status().isBadRequest());
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteCode);
    }
}
