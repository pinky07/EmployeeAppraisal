package com.gft.employeeappraisal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * TODO Document this!
 *
 * @author Ruben Jimenez
 */
@Controller
public class SwaggerController {

    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public String swaggerUI() {
        return "redirect:swagger-ui.html";
    }
}