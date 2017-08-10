package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.configuration.BeanConfiguration;
import com.gft.employeeappraisal.configuration.ControllerConfiguration;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test class for the {@link AppraisalsController} controller class, GET endpoints.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AppraisalsController.class)
@Import({ControllerConfiguration.class, BeanConfiguration.class})
public class AppraisalsControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private AppraisalService appraisalService;

    @Test
    public void employeesEmployeeIdAppraisalsAppraisalIdFormsFormIdGet() throws Exception {
    }

    @Test
    public void employeesEmployeeIdAppraisalsAppraisalIdFormsGet() throws Exception {
    }

    @Test
    public void employeesEmployeeIdAppraisalsAppraisalIdGet() throws Exception {
    }

    @Test
    public void employeesEmployeeIdAppraisalsGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdFormsFormIdGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdFormsGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdGet() throws Exception {
    }

    @Test
    public void meAppraisalsGet() throws Exception {
    }

}