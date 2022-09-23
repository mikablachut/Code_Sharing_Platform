package com.codeSharingPlatform;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.repositories.CodeRepository;
import com.codeSharingPlatform.services.CodeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class WebControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CodeService createCodeServiceMock;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeRepository codeRepository;

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
        Code isPresent = codeRepository.findCodeById("3");

        assertNotNull(isPresent);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/code/{id}", 3))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");

    }

    @Test
    public void getLatestCodeHttpRequest() throws Exception {
        List<Code> latestCode = codeRepository.findAllByOrderByDateDesc();

        assertEquals(2, latestCode.size());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/code/latest"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "latestCode");
    }

    @Test
    public void addCodeHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/code/new"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "newCode");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteCode);
    }

}
