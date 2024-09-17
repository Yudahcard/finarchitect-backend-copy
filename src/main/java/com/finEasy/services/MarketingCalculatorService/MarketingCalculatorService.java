package com.finEasy.services.MarketingCalculatorService;

import com.finEasy.models.Response;
import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.entity.MarketingDetails.MarketingOutput;
import com.finEasy.models.entity.MarketingDetails.MonthlyProjection;
import com.finEasy.models.entity.Product;

import java.util.List;
import java.util.Map;

public interface MarketingCalculatorService {


    public int getCustomerAcquisitionCostOnline(String productOrCompany);

//    public Map<String, Double> getProspectiveProjectedMonthlyOfCustomersOnLine(List<Product> products, double monthlyCustomers);

    public Map<String, Double> getCalculateCustomerAcquisitionCost(List<Product> products, double monthlyCustomers);
}
