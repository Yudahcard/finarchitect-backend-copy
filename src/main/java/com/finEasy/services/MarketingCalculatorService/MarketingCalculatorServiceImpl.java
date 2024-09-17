package com.finEasy.services.MarketingCalculatorService;

import com.finEasy.models.Response;
import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.entity.MarketingDetails.MarketingOutput;
import com.finEasy.models.entity.MarketingDetails.MonthlyProjection;
import com.finEasy.models.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketingCalculatorServiceImpl implements MarketingCalculatorService{


    @Override
    public int getCustomerAcquisitionCostOnline(String productOrCompany) {


        return 40;
    }

//    @Override
//    public Map<String, Double> getProspectiveProjectedMonthlyOfCustomersOnLine(List<Product> products, double monthlyCustomers) {
//
//        return  ;
//    }

    @Override
    public Map<String, Double> getCalculateCustomerAcquisitionCost(List<Product> products, double monthlyCustomers) {


        return null;
    }
}
