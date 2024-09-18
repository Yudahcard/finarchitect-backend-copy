package com.finEasy.models.entity.MarketingDetails;

import lombok.Data;

import java.util.Map;

@Data
public class MonthlyProjection {

    private int month;
    private Map<String, Double> productsRevenue;

    private String monthlyCustomers;
}
