package com.gft.employeeappraisal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"default", "oauth2", "local-postgresql"})
public class ApplicationContextTest {

    @Test
    public void applicationContextLoads() {
        System.out.println("Application Context Loaded!");
    }
}
