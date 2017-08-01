package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.ServiceSpringBootUnitTest;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Service layer test for {@link RelationshipService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class RelationshipServiceTest extends ServiceSpringBootUnitTest {

	@Autowired
	private RelationshipService relationshipService;

	private Relationship relationship;

	@Test
	public void findById() throws Exception {
		relationship = relationshipService.findById(RelationshipName.OTHER.getId()).get();
		assertNotNull(relationship);
	}

	@Test
	public void findById_invalid() throws Exception {
		assertEquals(Optional.empty(), relationshipService.findById(-100));
	}

	@Test
	public void findByName() throws Exception {
		relationship = relationshipService.findByName(RelationshipName.OTHER);
		assertNotNull(relationship);
	}
}
