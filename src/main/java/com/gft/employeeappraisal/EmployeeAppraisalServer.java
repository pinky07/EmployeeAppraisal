package com.gft.employeeappraisal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application class runner for the EmployeeAppraisal microservice.
 *
 * @author Ruben Jimenez
 * @author Manuel Yepez
 */
@SpringBootApplication
public class EmployeeAppraisalServer {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeAppraisalServer.class, args);
    }
}
