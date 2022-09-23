package com.codeSharingPlatform;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.exceptions.CodeNotFoundException;
import com.codeSharingPlatform.repositories.CodeRepository;
import com.codeSharingPlatform.services.CodeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class CodeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

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
    public void saveCodeService() {
        Code code = new Code("1","Secret code","2022-09-21 12:20:47:105", 6000L, 5L,
                true,true);
        Code codeWithoutRestriction = new Code("2","Top Secret code","2022-09-21 12:25:55:110",
                0L,0L,false,false);

        codeService.save(code);
        codeService.save(codeWithoutRestriction);

        Code codeToVerify = codeRepository.findCodeById("1");
        Code codeWithoutRestrictionToVerify = codeRepository.findCodeById("2");

        assertEquals("Secret code", codeToVerify.getCode());
        assertEquals("Top Secret code", codeWithoutRestrictionToVerify.getCode());
    }

    @Test
    public void getCodeByIdService() throws InterruptedException {
        Code codeToUse = new Code();
        String time = codeToUse.formatDate(LocalDateTime.now());
        Code toSave = new Code("5","class Car { ...",time,8000L,10L,true,true);

        codeRepository.save(toSave);

        Thread.sleep(1000);

        Code code = codeService.getCodeByID("3");
        Code codeWithRestriction = codeService.getCodeByID("5");

        assertEquals("public static void ...", code.getCode());
        assertEquals(0, code.getViews());
        assertEquals(0, code.getTime());

        assertEquals("class Car { ...", codeWithRestriction.getCode());
        assertEquals(9,codeWithRestriction.getViews());
        assertNotEquals(codeWithRestriction.getTime(), 8000);
    }

    @Test
    public void getLatestCodeService() {
        List<Code> latestCode = codeService.getLatestCode();

        assertEquals(2, latestCode.size());
        assertEquals("public static void ...", latestCode.get(0).getCode());
        assertEquals("class Code { ...", latestCode.get(1).getCode());
    }

    @Test
    public void ChangeTimeAndViewServiceException() throws InterruptedException {
        Code codeToUse = new Code();
        String time = codeToUse.formatDate(LocalDateTime.now());
        Code testViewRestriction = new Code("5","class Car { ...",time,8000L,2L,true,
                true);

        Code testTimeViewRestriction = new Code("6","public static void ...", time, 1L,2L,
                true,true);

        codeService.save(testViewRestriction);
        codeService.save(testTimeViewRestriction);

        Thread.sleep(1000);

        codeService.changeTimeAndViews(codeService.getCodeByID("5"));

        Exception exception = assertThrows(CodeNotFoundException.class, () -> {
            codeService.getCodeByID("5");
        });

        Exception exceptionWithTime = assertThrows(CodeNotFoundException.class, () -> {
            codeService.getCodeByID("6");
        });
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteCode);
    }
}
