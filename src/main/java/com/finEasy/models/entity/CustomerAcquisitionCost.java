package com.finEasy.models.entity;

import lombok.Data;

@Data
public class CustomerAcquisitionCost {

    private double cac;
    private double monthlyMarketingCost;

    private double projectedNumberOfCustomersMonthly;

    private double growthRate;


}
