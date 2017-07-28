package com.gft.employee.configuration;

import com.gft.employee.controller.EntityDTOComparator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Controller Tests
 *
 * @author Rubén Jiménez
 */
@Configuration
public class ControllerConfiguration {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EntityDTOComparator getEntityDTOComparator() {
        return new EntityDTOComparator();
    }
}
