package com.finEasy.models.controller;

import com.finEasy.models.entity.company.Company;
import com.finEasy.models.entity.company.CompanyResponse;
import com.finEasy.models.services.CompanyService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("api/signUpController/")
public class signUpController {

    private final static Logger logger = LoggerFactory.getLogger(signUpController.class);

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/signupCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> signupCustomer(@RequestBody String request,
                                                         @RequestHeader HttpHeaders httpHeaders, final HttpServletRequest httpServletRequest) throws Exception {

        String sourcecode = httpServletRequest.getHeader("x-source-code");

        Company companyReq = new Company();
        CompanyResponse companyResponse = new CompanyResponse();


        try {
            Gson gs = new Gson();
            companyReq = gs.fromJson(request, Company.class);

            logger.info("Request: " + request);
            logger.info("Response:Response name:"+ companyReq.getName()+" "+ companyReq.getIndustry());

            companyService.registerCompany(companyReq);


            companyResponse.setResponseCode("99");
            companyResponse.setResponseMessage("Company Created Successfully");
////            resp.setRequestId(req.getRequestId());
////            resp.setIpAddress(req.getIpAddress());
////            resp.setRequestToken(req.getRequestToken());
////            resp.setRequestType(req.getRequestType());
////            resp.setSourceChannelId(req.getSourceChannelId());
////            resp.setResponseCode("E04");
////            resp.setResponseMessage(ex.getMessage());

        } catch (Throwable ex) {
//
            companyResponse.setResponseCode("E04");
            companyResponse.setResponseMessage(ex.getMessage());
            logger.info("Throwable caught: " + ex.getMessage());
            logger.info("Reason: " + Arrays.toString(ex.getStackTrace()).replace(", ", "\n"));

        }

        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }



}
