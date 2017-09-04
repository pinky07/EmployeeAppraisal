package com.gft.employeeappraisal.configuration;

import com.gft.employeeappraisal.helper.comparator.EntityDTOComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Test Beans
 *
 * @author Rubén Jiménez
 */

@Profile("test")
@Configuration
public class TestBeanConfiguration {

    @Bean
    public EntityDTOComparator getEntityDTOComparator() {
        return new EntityDTOComparator();
    }
}
