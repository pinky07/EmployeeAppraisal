package com.gft.employeeappraisal.configuration;

import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOMapper;
import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.converter.employee.EmployeeDTOMapper;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOConverter;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOMapper;
import com.gft.employeeappraisal.converter.validator.EmployeeDTOToEntityCreateValidator;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
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
		DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.registerMapper(new EmployeeDTOMapper());
		mapperFactory.registerMapper(new EmployeeRelationshipDTOMapper());
		ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory.registerConverter(new AppraisalDTOConverter());
		return mapperFactory;
	}

	@Bean
	public EmployeeDTOConverter employeeDTOConverter() {
    	return new EmployeeDTOConverter(mapperFactory());
	}

	@Bean
	public EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter() {
    	return new EmployeeRelationshipDTOConverter(mapperFactory());
	}

	/**
	 * Bean instantiation for the bidirectional AppraisalDTO mapper.
	 * @return AppraisalDTOMapper instance.
	 */
	@Bean
	public AppraisalDTOMapper appraisalDTOMapper() {
    	return new AppraisalDTOMapper(mapperFactory());
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
