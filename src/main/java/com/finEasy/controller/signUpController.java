package com.finEasy.controller;

import com.finEasy.services.CompanyService;
//import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/fin-architect/")
public class signUpController {

    private final static Logger logger = LoggerFactory.getLogger(signUpController.class);

    @Autowired
    private CompanyService companyService;


}
