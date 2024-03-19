package com.finEasy.models.entity.MarketingDetails;

import com.finEasy.models.entity.Product;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class MarketingDetails {

    public MarketingDetails(double monthlyMarketingCost, int projectedMonthlyCustomers, double growthRate) {
        this.monthlyMarketingCost = monthlyMarketingCost;
        this.projectedMonthlyCustomers = projectedMonthlyCustomers;
        this.growthRate = growthRate;
//        this.products = products;
    }



    @Id
    private int companyId;
    private double monthlyMarketingCost; // MMC
    private int projectedMonthlyCustomers;
    private double growthRate;
//    private List<Product> products;

}
