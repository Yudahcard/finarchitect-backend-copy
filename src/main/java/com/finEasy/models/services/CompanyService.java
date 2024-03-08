package com.finEasy.models.services;

import com.finEasy.models.entity.company.Company;
import com.finEasy.models.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {


    @Autowired
    private CompanyRepository companyRepository;


    public Company registerCompany(Company company) {
        // Additional business logic, validation, or processing can be added here
        return companyRepository.save(company);
    }


}
