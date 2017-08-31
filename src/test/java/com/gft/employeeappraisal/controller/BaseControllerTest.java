package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for controller layer tests.
 *
 * @author Manuel Yepez
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles({ "default", "local", "test" })
public abstract class BaseControllerTest {

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected MockMvc mockMvc;
}
