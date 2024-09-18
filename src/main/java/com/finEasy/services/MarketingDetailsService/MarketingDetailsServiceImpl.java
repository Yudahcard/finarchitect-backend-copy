package com.finEasy.services.MarketingDetailsService;


import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.entity.Product;
import com.finEasy.models.repository.MarketingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarketingDetailsServiceImpl implements MarketingDetailsService{


    @Autowired
    public MarketingDetailsRepository marketingDetailsRepository;

    public void SaveMarketingDetails(MarketingDetails marketingDetails){

        marketingDetailsRepository.save(marketingDetails);
    }




    @Override
    public int getCustomerAcquisitionCostOnline(String productOrCompany) {


        return 40;
    }


    @Override
    public Map<String, Double> getCalculateCustomerAcquisitionCost(List<Product> products, double monthlyCustomers) {


        return null;
    }



    //    @Override
//    public Map<String, Double> getProspectiveProjectedMonthlyOfCustomersOnLine(List<Product> products, double monthlyCustomers) {
//
//        return  ;
//    }
}
