package com.gft.employeeappraisal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"default", "oauth2", "local-postgresql"})
public class ApplicationContextTest {

    private final Logger logger = LoggerFactory.getLogger(ApplicationContextTest.class);

    @Test
    public void applicationContextLoads() {
        logger.debug("Application Context Loaded!");
    }
}
