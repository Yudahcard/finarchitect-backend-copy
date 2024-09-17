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

    public MarketingDetails(int monthlyMarketingCost, int projectedMonthlyCustomers, int cac) {
        this.monthlyMarketingCost = monthlyMarketingCost;
        this.projectedMonthlyCustomers = projectedMonthlyCustomers;
    }

    public MarketingDetails() {

    }


    private Integer monthlyMarketingCost;
    private Integer projectedMonthlyCustomers;
    private Integer cac;


}
