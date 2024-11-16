package com.finEasy.models.entity.MarketingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingModelResponse {
    private String responseCode;
    private String responseMessage;
    private MarketingModelOutput marketingOutput;
}
