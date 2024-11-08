package com.finEasy.models.entity.MarketingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marketing_model_output")
public class MarketingModelOutput {
    private Double monthlyMarketingCost;
    private Double initialConvertedCustomers;
    private Map<Integer, Double> monthlyCustomerGrowth;
    private Double totalYearlyCustomers;
}
