package com.gft.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application class runner for the Employee microservice.
 *
 * @author Ruben Jimenez
 */
@SpringBootApplication
public class EmployeeServer {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServer.class, args);
    }
}
