package com.finEasy.models.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Product {

    @javax.persistence.Id
    private  int Id;
    private String name;
    private String description;
    private double pricePerUnit;
    private double salesVolume;


    private int calculateProductPercentageSpread(int salesVolume){

        // To calculate this we have to get the number of products, the sales volume for each product
        // then we can get the percentage for each product.

        return salesVolume;

    };
}
