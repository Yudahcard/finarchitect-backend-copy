package com.finEasy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finEasy.models.ResponseOld;
import com.finEasy.models.entity.MarketingDetails.MarketingModelInput;
import com.finEasy.models.entity.MarketingDetails.MarketingModelOutput;
import com.finEasy.models.entity.MarketingDetails.MarketingModelResponse;
import com.finEasy.services.MarketingDetailsService.MarketingDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/marketing")
public class MarketingModelController {

    private final static Logger logger = LoggerFactory.getLogger(MarketingModelController.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MarketingDetailsService marketingDetailServiceImpl;

    @PostMapping(value = "/calculateMarketingMetrics", consumes = "text/plain", produces = "text/plain")
    public ResponseEntity<String> calculateMarketingMetrics(@RequestBody String request,
                                                            final HttpServletRequest httpServletRequest) {

        logger.info("Incoming request: {}", request);
//        ResponseOld responseOld = new ResponseOld();

        MarketingModelResponse response = new MarketingModelResponse();

        try {
            // Parse input model
            MarketingModelInput inputModel = objectMapper.readValue(request, MarketingModelInput.class);

            // Validate required inputs
            if (inputModel.getYearlyMarketingCost() == null ||
                    inputModel.getCac() == null ||
                    inputModel.getCustomerGrowthRate() == null) {

                response.setResponseCode("400");
                response.setResponseMessage("Missing required inputs");
                return ResponseEntity.badRequest().body(response.toString());
            }

            // Create output model and perform calculations
            MarketingModelOutput outputModel = calculateMetrics(inputModel);

            response.setResponseCode("00");
            response.setResponseMessage("Success");
            response.setMarketingOutput(outputModel);

            // Save both input and output models
            marketingDetailServiceImpl.saveMarketingDetails(inputModel);

        } catch (JsonProcessingException e) {
            logger.error("Error processing request", e);
            response.setResponseCode("99");
            response.setResponseMessage("Error processing request: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response.toString());

        }

        return ResponseEntity.ok(response.toString());
    }

    private MarketingModelOutput calculateMetrics(MarketingModelInput input) {
        MarketingModelOutput output = new MarketingModelOutput();

        // Calculate monthly marketing cost
        double monthlyMarketingCost = input.getYearlyMarketingCost() / 12.0;
        output.setMonthlyMarketingCost(monthlyMarketingCost);

        // Calculate initial converted customers (Month 1)
        double initialConvertedCustomers = monthlyMarketingCost / input.getCac();
        output.setInitialConvertedCustomers(initialConvertedCustomers);

        // Calculate customer growth for months 2-12
        Map<Integer, Double> monthlyCustomers = new HashMap<>();
        monthlyCustomers.put(1, initialConvertedCustomers);

        double growthRate = input.getCustomerGrowthRate() / 100.0; // Convert percentage to decimal

        for (int month = 2; month <= 12; month++) {
            double previousMonthCustomers = monthlyCustomers.get(month - 1);
            double currentMonthCustomers = previousMonthCustomers * (1 + growthRate);
            monthlyCustomers.put(month, currentMonthCustomers);
        }

        output.setMonthlyCustomerGrowth(monthlyCustomers);

        // Calculate total customers for the year
        double totalCustomers = monthlyCustomers.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        output.setTotalYearlyCustomers(totalCustomers);

        return output;
    }
}
