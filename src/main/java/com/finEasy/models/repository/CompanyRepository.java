package com.finEasy.models.repository;


import com.finEasy.models.entity.company.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("destinationDataSource")
public interface CompanyRepository extends JpaRepository<Company, Long> {



}
