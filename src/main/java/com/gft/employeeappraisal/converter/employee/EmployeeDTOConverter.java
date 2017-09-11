package com.gft.employeeappraisal.converter.employee;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Employee;
import com.gft.swagger.employees.model.EmployeeDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link Employee} and {@link EmployeeDTO}.
 *
 * @author Manuel Yepez
 */
@Component
public class EmployeeDTOConverter extends EntityDTOConverter<Employee, EmployeeDTO> {

    @Autowired
    public EmployeeDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(Employee.class, EmployeeDTO.class);
    }
}
