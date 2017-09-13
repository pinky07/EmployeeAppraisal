package com.gft.employeeappraisal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// Disabling this test temporarily
// @RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"default", "oauth2", "local-h2"})
public class ApplicationContextTest {

    private final Logger logger = LoggerFactory.getLogger(ApplicationContextTest.class);

    @Test
    public void applicationContextLoads() {
        logger.debug("Application Context Loaded!");
    }
}
