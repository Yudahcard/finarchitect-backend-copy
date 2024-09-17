package com.finEasy.services;


import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.entity.MarketingDetails.MarketingDetailsRequest;
import com.finEasy.models.repository.MarketingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketingDetailsService {


    @Autowired
public MarketingDetailsRepository marketingDetailsRepository;

    public void InputMarketingDetails(MarketingDetails marketingDetails){

        marketingDetailsRepository.save(marketingDetails);
    }


}
