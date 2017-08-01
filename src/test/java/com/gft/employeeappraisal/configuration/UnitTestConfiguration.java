package com.gft.employeeappraisal.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for service layer testing.
 *
 * @author Manuel Yepez
 */
@TestConfiguration
@ComponentScan(basePackages = "com.gft.employeeappraisal.service")
@EnableJpaRepositories("com.gft.employeeappraisal.repository")
public class UnitTestConfiguration {
}
