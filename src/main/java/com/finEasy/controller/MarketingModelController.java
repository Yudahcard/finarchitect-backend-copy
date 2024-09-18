package com.finEasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finEasy.models.Response;
import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.utilities.EncryptionService;
import com.finEasy.models.utilities.ResponseEnum;
import com.finEasy.services.MarketingDetailsService.MarketingDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/fin-architect")
public class MarketingModelController {

        private final static Logger logger = LoggerFactory.getLogger(MarketingModelController.class);

        private final static ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        private MarketingDetailsService marketingDetailServiceImpl;

        @Autowired
        EncryptionService encryptionService;



        @PostMapping(value = "/marketingModelEndPoint", consumes = "text/plain", produces = "text/plain")
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

                onlineCac = marketingDetailServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

                marketingModelDetails.setCac(onlineCac);

                response.setMarketingDetails(marketingModelDetails);

                // save the marketing details to marketing details table
                marketingDetailServiceImpl.SaveMarketingDetails(marketingModelDetails);


            } else if ((marketingModelDetails.getMonthlyMarketingCost() != null) &&(marketingModelDetails.getProjectedMonthlyCustomers() == null) && (marketingModelDetails.getCac() == null)) {

                onlineCac = marketingDetailServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

                marketingModelDetails.setCac(onlineCac);

                marketingModelDetails.setProjectedMonthlyCustomers(marketingModelDetails.getMonthlyMarketingCost()/onlineCac);

                response.setMarketingDetails(marketingModelDetails);

                // save the marketing details to marketing details table
                marketingDetailServiceImpl.SaveMarketingDetails(marketingModelDetails);

            }


            //   return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
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
//            return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
            return ResponseEntity.ok(response.toString());

        }
//        return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
        return ResponseEntity.ok(response.toString());
    }






    public ResponseEntity<String> getCacOnline()
            throws Exception {
//        String sourcecode = httpServletRequest.getHeader("x-source-code");
        Response response = new Response();

        int onlineCac;


        try {

//            response = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");
            onlineCac = marketingDetailServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

        } catch (Exception ex) {
            logger.error("Operation Failed", ex);

            response.setResponseCode("99");
            response.setResponseMessage(ResponseEnum.RECORD_NOT_FOUND.toString());
            return ResponseEntity.ok(response.toString());

        }
        return ResponseEntity.ok(response.toString());
    }






    }

