package com.gft.employeeappraisal;

import com.gft.employeeappraisal.configuration.UnitTestConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Abstract class meant for general service unit tests on the Employee microservice.
 * It automatizes several annotation configurations onto a single one.
 *
 * @author Manuel Yepez
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = { UnitTestConfiguration.class })
@DataJpaTest
public abstract class ServiceSpringBootUnitTest {
}
