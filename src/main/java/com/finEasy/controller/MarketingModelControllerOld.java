package com.finEasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finEasy.models.ResponseOld;
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
public class MarketingModelControllerOld {

        private final static Logger logger = LoggerFactory.getLogger(MarketingModelControllerOld.class);

        private final static ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        private MarketingDetailsService marketingDetailServiceImpl;

        @Autowired
        EncryptionService encryptionService;



        @PostMapping(value = "/marketingModelEndPoint", consumes = "text/plain", produces = "text/plain")
        public ResponseEntity<String> marketingModelEndPoint(@RequestBody String requests, final HttpServletRequest httpServletRequest){

            logger.info("request===" + requests);

            ResponseOld responseOld = new ResponseOld();

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

                responseOld.setMarketingDetails(marketingModelDetails);

                // save the marketing details to marketing details table
                marketingDetailServiceImpl.SaveMarketingDetails(marketingModelDetails);


            } else if ((marketingModelDetails.getMonthlyMarketingCost() != null) &&(marketingModelDetails.getProjectedMonthlyCustomers() == null) && (marketingModelDetails.getCac() == null)) {

                onlineCac = marketingDetailServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

                marketingModelDetails.setCac(onlineCac);

                marketingModelDetails.setProjectedMonthlyCustomers(marketingModelDetails.getMonthlyMarketingCost()/onlineCac);

                responseOld.setMarketingDetails(marketingModelDetails);

                // save the marketing details to marketing details table
                marketingDetailServiceImpl.SaveMarketingDetails(marketingModelDetails);

            }


            //   return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
            return ResponseEntity.ok(responseOld.toString());
        }




    public ResponseEntity<String> getMarketingModelOutput(@RequestBody String request,final HttpServletRequest httpServletRequest)
            throws Exception {
        String sourcecode = httpServletRequest.getHeader("x-source-code");
        ResponseOld responseOld = new ResponseOld();


        MarketingDetails marketingDetails = new MarketingDetails();


        try {

//            response = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");
        } catch (Exception ex) {
            logger.error("Operation Failed", ex);

            responseOld.setResponseCode("99");
            responseOld.setResponseMessage(ResponseEnum.RECORD_NOT_FOUND.toString());
//            return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
            return ResponseEntity.ok(responseOld.toString());

        }
//        return ResponseEntity.ok(encryptionService.encrypt(response, sourcecode));
        return ResponseEntity.ok(responseOld.toString());
    }






    public ResponseEntity<String> getCacOnline()
            throws Exception {
//        String sourcecode = httpServletRequest.getHeader("x-source-code");
        ResponseOld responseOld = new ResponseOld();

        int onlineCac;


        try {

//            response = marketingCalculatorServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");
            onlineCac = marketingDetailServiceImpl.getCustomerAcquisitionCostOnline("productOrCompany");

        } catch (Exception ex) {
            logger.error("Operation Failed", ex);

            responseOld.setResponseCode("99");
            responseOld.setResponseMessage(ResponseEnum.RECORD_NOT_FOUND.toString());
            return ResponseEntity.ok(responseOld.toString());

        }
        return ResponseEntity.ok(responseOld.toString());
    }






    }

