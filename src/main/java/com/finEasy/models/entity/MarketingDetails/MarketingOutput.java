package com.finEasy.models.entity.MarketingDetails;

import lombok.Data;

import java.util.List;

@Data
public class MarketingOutput {

    private double customerAcquisitionCost;
    private List<MonthlyProjection> monthlyProjections;
}
