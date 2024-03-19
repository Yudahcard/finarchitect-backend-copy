package com.finEasy.models.controller;

import com.finEasy.models.entity.company.Company;
import com.finEasy.models.entity.company.CompanyResponse;
import com.finEasy.models.services.CompanyService;
import com.google.gson.Gson;
//import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("api/signUpController/")
public class signUpController {

    private final static Logger logger = LoggerFactory.getLogger(signUpController.class);

    @Autowired
    private CompanyService companyService;


}
