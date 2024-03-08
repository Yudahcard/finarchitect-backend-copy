package com.finEasy.models.entity.company;

import com.finEasy.models.entity.Stage;
import lombok.Data;

@Data
public class Company {

    private String name;
    private String currency;
    private String country;
    private int foundingYear;
    private String industry;
    private String size;
    private Stage stage;

}
