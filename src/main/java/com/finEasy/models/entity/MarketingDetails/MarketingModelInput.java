package com.finEasy.models.entity.MarketingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingModelInput {

    private int companyId;

    private int customerId;
    private Double yearlyMarketingCost;
    private Double cac;
    private Double customerGrowthRate;


}
