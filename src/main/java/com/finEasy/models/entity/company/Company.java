package com.finEasy.models.entity.company;

import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String currency;
    private String country;
    private int foundingYear;
    private String industry;
    private String size;
    private Stage stage;

//    private MarketingDetails marketingDetails;

}
