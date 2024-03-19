package com.finEasy.models.entity.MarketingDetails;


import lombok.Data;

import javax.persistence.Entity;

@Data
public class MarketingDetailsRequest {

    private int companyId;
    private MarketingDetails marketingDetails;

}
