package com.gft.employeeappraisal.configuration;

import com.gft.employeeappraisal.converter.employee.EmployeeDTOFromEntity;
import com.gft.employeeappraisal.converter.employee.EmployeeDTOToEntity;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOFromEntity;
import com.gft.employeeappraisal.converter.validator.EmployeeDTOToEntityCreateValidator;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

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
	 * @return Mapper Factory instance.
	 */
    @Bean
    public MapperFactory mapperFactory() {
    	return new DefaultMapperFactory.Builder().build();
	}

	/**
	 * Bean instantiation for the EmployeeDTO converter (from Employee).
	 * @return EmployeeDTOFromEntity instance.
	 */
	@Bean
	public EmployeeDTOFromEntity employeeDTOFromEntity() {
		return new EmployeeDTOFromEntity(mapperFactory());
	}

	/**
	 * Bean instantiation for the EmployeeDTO converter (to Employee).
	 * @return EmployeeDTOToEntity instance.
	 */
	@Bean
	public EmployeeDTOToEntity employeeDTOToEntity() {
    	return new EmployeeDTOToEntity(mapperFactory());
	}

	/**
	 * Bean instantiation for the EmployeeRelationshipDTO converter (from EmployeeRelationship).
	 * @return EmployeeRelationshipDTOFromEntity instance.
	 */
	@Bean
	public EmployeeRelationshipDTOFromEntity employeeRelationshipDTOFromEntity() {
		return new EmployeeRelationshipDTOFromEntity(mapperFactory());
	}

	/**
	 * Bean instantiation for the validator used on Employee creation endpoints.
	 * @return EmployeeDTOToEntityCreateValidator instance.
	 */
	@Bean
	public EmployeeDTOToEntityCreateValidator employeeDTOToEntityCreateValidator() {
    	return new EmployeeDTOToEntityCreateValidator();
	}

	/**
	 * Bean instantiation for the reloadable message source used for system messages.
	 * Changes made on the messages.properties file are reloaded without needing to restart the server.
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
