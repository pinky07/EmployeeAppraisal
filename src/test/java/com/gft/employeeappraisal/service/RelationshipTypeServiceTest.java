package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.model.RelationshipType;
import com.gft.employeeappraisal.service.impl.RelationshipTypeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Service layer test for {@link RelationshipTypeService}
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
public class RelationshipTypeServiceTest extends BaseServiceTest {

    // Class under test
    private RelationshipTypeService relationshipTypeService;

    @Before
    public void setUp() throws Exception {
        this.relationshipTypeService = new RelationshipTypeServiceImpl(
                this.relationshipTypeRepository);
    }

    @Test
    public void getById() throws Exception {
        RelationshipType relationshipType = relationshipTypeService.getById(RelationshipName.PEER.getId());
        assertNotNull(relationshipType);
    }

    @Test(expected = NotFoundException.class)
    public void getById_InvalidRelationshipId() throws Exception {
        relationshipTypeService.getById(-100);
    }

    @Test
    public void findById() throws Exception {
        Optional<RelationshipType> relationship = relationshipTypeService.findById(RelationshipName.OTHER.getId());
        assertTrue(relationship.isPresent());
    }

    @Test
    public void findById_invalid() throws Exception {
        assertEquals(Optional.empty(), relationshipTypeService.findById(-100));
    }

    @Test
    public void findByName() throws Exception {
        RelationshipType relationshipType = relationshipTypeService.findByName(RelationshipName.OTHER);
        assertNotNull(relationshipType);
    }

    @Test
    public void findByNames() throws Exception {
        List<RelationshipType> relationshipTypes = relationshipTypeService
                .findRelationshipsByNames(RelationshipName.SELF, RelationshipName.OTHER)
                .collect(Collectors.toList());

        assertTrue(relationshipTypes.size() == 2);
        assertTrue(relationshipTypes.contains(relationshipTypeService.findByName(RelationshipName.OTHER)));
        assertTrue(relationshipTypes.contains(relationshipTypeService.findByName(RelationshipName.SELF)));
    }
}
