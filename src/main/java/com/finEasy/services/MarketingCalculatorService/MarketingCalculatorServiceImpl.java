package com.finEasy.services.MarketingCalculatorService;

import com.finEasy.models.entity.Product;
import org.springframework.stereotype.Service;

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
