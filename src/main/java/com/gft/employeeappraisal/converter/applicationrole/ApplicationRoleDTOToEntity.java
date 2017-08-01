package com.gft.employeeappraisal.converter.applicationrole;

import com.gft.employeeappraisal.converter.Mapper;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class that contains map specifications for converting an ApplicationRoleDTO object into
 * ApplicationRole.
 *
 * @author Manuel Yepez
 */
public class ApplicationRoleDTOToEntity implements Mapper<ApplicationRoleDTO, ApplicationRole> {

	private MapperFactory mapperFactory;

	@Autowired
	public ApplicationRoleDTOToEntity(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
	}

	/**
	 * @see Mapper#map(Object)
	 * @param source {@link ApplicationRoleDTO} ApplicationRoleDTO object to be converted.
	 * @return {@link ApplicationRole} Transformed ApplicationRole object.
	 */
	@Override
	public ApplicationRole map(ApplicationRoleDTO source) {
		return this.mapperFactory.getMapperFacade().map(source, ApplicationRole.class);
	}
}
