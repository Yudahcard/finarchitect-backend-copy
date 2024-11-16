package com.finEasy.services.MarketingCalculatorService;

import com.finEasy.models.entity.Product;

import java.util.List;
import java.util.Map;

public interface MarketingCalculatorService {


    public int getCustomerAcquisitionCostOnline(String productOrCompany);

//    public Map<String, Double> getProspectiveProjectedMonthlyOfCustomersOnLine(List<Product> products, double monthlyCustomers);

    public Map<String, Double> getCalculateCustomerAcquisitionCost(List<Product> products, double monthlyCustomers);

}
