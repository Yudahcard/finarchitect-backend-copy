package com.finEasy.models.entity;

import lombok.Data;

@Data
public class Product {
    private String name;
    private String description;
    private double pricePerUnit;
    private double percentageSpreadInRevenue;
}
