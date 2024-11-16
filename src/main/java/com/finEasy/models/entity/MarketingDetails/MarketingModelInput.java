package com.finEasy.models.entity.MarketingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marketing_input")
public class MarketingModelInput {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int companyId;

    private int customerId;
    private Double yearlyMarketingCost;
    private Double cac;
    private Double customerGrowthRate;


}
