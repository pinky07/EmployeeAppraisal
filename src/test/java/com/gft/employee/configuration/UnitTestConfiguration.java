package com.gft.employee.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for service layer testing.
 *
 * @author Manuel Yepez
 */
@TestConfiguration
@ComponentScan(basePackages = "com.gft.employee.service")
@EnableJpaRepositories("com.gft.employee.repository")
public class UnitTestConfiguration {
}
