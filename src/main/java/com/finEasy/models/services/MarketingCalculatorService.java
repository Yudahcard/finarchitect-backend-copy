package com.finEasy.models.services;

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
public class MarketingCalculatorService {

    public MarketingOutput calculateMarketingProjections(MarketingDetails details) {
        MarketingOutput output = new MarketingOutput();
        output.setCustomerAcquisitionCost(calculateCustomerAcquisitionCost(details));
        output.setMonthlyProjections(calculateMonthlyProjections(details));
        return output;
    }

    private double calculateCustomerAcquisitionCost(MarketingDetails marketingDetails) {
        return marketingDetails.getMonthlyMarketingCost() / marketingDetails.getProjectedMonthlyCustomers();
    }

    private List<MonthlyProjection> calculateMonthlyProjections(MarketingDetails marketingDetails) {
        List<MonthlyProjection> projections = new ArrayList<>();
        double monthlyCustomers = marketingDetails.getProjectedMonthlyCustomers();
        for (int month = 1; month <= 12; month++) { // Assuming we're projecting for 12 months
            MonthlyProjection projection = new MonthlyProjection();
            projection.setMonth(month);
//            projection.setProductsRevenue(calculateRevenueForMonth(marketingDetails.getProducts(), monthlyCustomers));
            projections.add(projection);
            monthlyCustomers *= (1 + marketingDetails.getGrowthRate());
        }
        return projections;
    }

    private Map<String, Double> calculateRevenueForMonth(List<Product> products, double monthlyCustomers) {
        Map<String, Double> revenue = new HashMap<>();
        for (Product product : products) {
            double monthlyCustomersPerProduct = monthlyCustomers * (product.getSalesVolume() / 100.0); // Assuming Sales Volume is a percentage
            double revenuePerProduct = monthlyCustomersPerProduct * product.getPricePerUnit();
            revenue.put(product.getName(), revenuePerProduct);
        }
        return revenue;
    }
}
