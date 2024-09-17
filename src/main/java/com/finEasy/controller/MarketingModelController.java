package com.finEasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finEasy.exceptions.BadHeaderValuesException;
import com.finEasy.models.Response;
import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.entity.MarketingDetails.MonthlyProjection;
import com.finEasy.models.utilities.EncryptionService;
import com.finEasy.models.utilities.ResponseEnum;
import com.finEasy.services.MarketingCalculatorService.MarketingCalculatorService;
import com.finEasy.services.MarketingCalculatorService.MarketingCalculatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("api/signUpController/")
public class MarketingModelController {

        private final static Logger logger = LoggerFactory.getLogger(MarketingModelController.class);

        private final static ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        private MarketingCalculatorService marketingCalculatorServiceImpl;

        @Autowired
        EncryptionService encryptionService;



        @PostMapping(value = "marketingModelEndPoint", consumes = "text/plain", produces = "text/plain")
        public ResponseEntity<String> marketingModelEndPoint(@RequestBody String requests, final HttpServletRequest httpServletRequest){

            logger.info("request===" + requests);

            Response response = new Response();

            String sourcecode = httpServletRequest.getHeader("x-source-code");

            MarketingDetails marketingModelDetails = new MarketingDetails();

            int onlineCac;

            try {
                marketingModelDetails = objectMapper.readValue(requests,MarketingDetails.class);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
                logger.error(e.getMessage());
//                throw new RuntimeException(e);
            }
// instance one
            if(((marketingModelDetails.getMonthlyMarketingCost()== null ) && (marketingModelDetails.getProjectedMonthlyCustomers() == null))){

                onlineCac = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

                marketingModelDetails.setCac(onlineCac);

                response.setMarketingDetails(marketingModelDetails);


            } else if ((marketingModelDetails.getMonthlyMarketingCost() != null) &&(marketingModelDetails.getProjectedMonthlyCustomers() == null) && (marketingModelDetails.getCac() == null)) {

                onlineCac = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

                marketingModelDetails.setCac(onlineCac);

                marketingModelDetails.setProjectedMonthlyCustomers(marketingModelDetails.getMonthlyMarketingCost()/onlineCac);


            }


            try {
                return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
            } catch (BadHeaderValuesException e) {
                throw new RuntimeException(e);
            }
        }


    public ResponseEntity<String> getCacOnline()
            throws Exception {
//        String sourcecode = httpServletRequest.getHeader("x-source-code");
        Response response = new Response();

        int onlineCac;


        try {

//            response = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");
            onlineCac = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

        } catch (Exception ex) {
            logger.error("Operation Failed", ex);

            response.setResponseCode("99");
            response.setResponseMessage(ResponseEnum.RECORD_NOT_FOUND.toString());
            return ResponseEntity.ok(response.toString());

        }
        return ResponseEntity.ok(response.toString());
    }




    public ResponseEntity<String> getMarketingModelOutput(@RequestBody String request,final HttpServletRequest httpServletRequest)
            throws Exception {
        String sourcecode = httpServletRequest.getHeader("x-source-code");
        Response response = new Response();


        MarketingDetails marketingDetails = new MarketingDetails();


        try {

//            response = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");
        } catch (Exception ex) {
            logger.error("Operation Failed", ex);

            response.setResponseCode("99");
            response.setResponseMessage(ResponseEnum.RECORD_NOT_FOUND.toString());
            return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));

        }
        return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
    }

    }

