package com.finEasy.models.repository;

import com.finEasy.models.entity.MarketingDetails.MarketingDetails;
import com.finEasy.models.entity.MarketingDetails.MarketingModelInput;
import com.finEasy.models.entity.company.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface MarketingDetailsRepository extends JpaRepository <MarketingModelInput, Long>{




}
