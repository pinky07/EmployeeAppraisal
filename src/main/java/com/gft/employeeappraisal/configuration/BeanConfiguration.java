package com.gft.employeeappraisal.configuration;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Set;

/**
 * Container class that keeps track of bean instantiations necessary for the EmployeeAppraisal microservice.
 *
 * @author Ruben Jimenez
 * @author Manuel Yepez
 */
@Configuration
public class BeanConfiguration {

    /**
     * Bean instantiation for Orika's mapper factory.
     *
     * @return Mapper Factory instance.
     */
    @Bean
    public MapperFactory mapperFactory(Set<CustomMapper<?, ?>> mappers) {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mappers.forEach(mapperFactory::registerMapper);
        return mapperFactory;
    }

    /**
     * Bean instantiation for the reloadable message source used for system messages.
     * Changes made on the messages.properties file are reloaded without needing to restart the server.
     *
     * @return MessageSource instance.
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
