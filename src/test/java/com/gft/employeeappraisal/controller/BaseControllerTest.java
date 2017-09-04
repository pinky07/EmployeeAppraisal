package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.employeeappraisal.configuration.ControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for controller layer tests.
 *
 * @author Manuel Yepez
 */
@ControllerTest
public abstract class BaseControllerTest {

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected MockMvc mockMvc;
}
