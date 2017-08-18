package com.gft.employeeappraisal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Redirects /swagger to /swagger-ui.html.
 *
 * @author Ruben Jimenez
 */
@Controller
public class SwaggerController {

    /**
     * Redirects /swagger to /swagger-ui.html.
     *
     * @return Swagger html page
     */
    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public String swaggerUI() {
        return "redirect:swagger-ui.html";
    }
}