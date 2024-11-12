package com.finEasy.controller;


import com.finEasy.models.entity.MarketingDetails.MarketingDetailsRequest;
import com.finEasy.models.entity.MarketingDetails.MarketingModelInput;
import com.finEasy.models.entity.Product;
import com.finEasy.models.entity.company.Company;
import com.finEasy.models.entity.company.CompanyResponse;
import com.finEasy.services.CompanyService;
import com.finEasy.services.MarketingDetailsService.MarketingDetailsServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("api/fin-architect/")
public class companyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MarketingDetailsServiceImpl marketingDetailsServiceImpl;
    private final static Logger logger = LoggerFactory.getLogger(companyController.class);
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




    @RequestMapping(value= "/addMarketingDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addMarketingDetails(@RequestBody String request, HttpServletRequest httpServletRequest) throws Exception{

//        MarketingDetailsRequest marketingDetailsRequest = new Gson().fromJson(request, MarketingDetailsRequest.class);

        MarketingModelInput marketingModelInput = new Gson().fromJson(request,MarketingModelInput.class);

//        Company companyTobeUpdated = companyService.getCompanyById(marketingDetailsRequest.getCompanyId());

//        Company companyTobeUpdated = companyService.getCompanyById(marketingModelInput.getCompanyId());


        List<Product> products = new ArrayList<Product>();

        // pick marketing details
        //pick company ID of logged-in user
        //get company object of logged-in user
        //ways we can do this, we can get token then create a method that uses the token, we can get company Id, we can get username of logged-in user.
        // update marketing details table with request

//        marketingDetailsServiceImpl.SaveMarketingDetails(marketingDetailsRequest.getMarketingDetails());

        marketingDetailsServiceImpl.saveMarketingDetails(marketingModelInput);

      return new ResponseEntity<>(HttpStatus.OK);

    }


}
