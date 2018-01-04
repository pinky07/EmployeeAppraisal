package com.gft.employeeappraisal.converter.relationshiptype;

import com.gft.employeeappraisal.model.RelationshipType;
import com.gft.swagger.employees.model.RelationshipTypeDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link RelationshipTypeDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class RelationshipTypeDTOMapper extends CustomMapper<RelationshipType, RelationshipTypeDTO> {

    @Override
    public void mapAtoB(RelationshipType relationshipType, RelationshipTypeDTO relationshipDTO, MappingContext context) {
        relationshipDTO.setId(relationshipType.getId());
        // replacing done to support TEAM_MEMBER => TEAM MEMBER for select options
        relationshipDTO.setName(relationshipType.getName().replace('_', ' '));
        relationshipDTO.setDescription(relationshipType.getDescription());
    }

    @Override
    public void mapBtoA(RelationshipTypeDTO relationshipDTO, RelationshipType relationshipType, MappingContext context) {
        if (relationshipDTO.getId() > 0) {
            relationshipType.setId(relationshipDTO.getId());
        }
        relationshipType.setName(relationshipDTO.getName());
        relationshipType.setDescription(relationshipDTO.getDescription());
    }
}
