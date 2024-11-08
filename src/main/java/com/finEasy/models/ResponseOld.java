package com.finEasy.models;


import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseOld {

    private String responseCode;
    private String responseMessage;
    private String token;
    private MarketingDetails marketingDetails;
    private List<Object> data = new ArrayList<>();

}
