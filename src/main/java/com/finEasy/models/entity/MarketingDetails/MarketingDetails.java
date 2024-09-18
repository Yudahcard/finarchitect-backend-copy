package com.finEasy.models.entity.MarketingDetails;

import com.finEasy.models.entity.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "marketing_details")
public class MarketingDetails {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Integer companyId;
    private Integer monthlyMarketingCost;
    private Integer projectedMonthlyCustomers;
    private Integer cac;


}
