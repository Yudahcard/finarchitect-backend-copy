package com.finEasy.models.repository;

import com.finEasy.models.customer.Customer;
import com.finEasy.models.customer.DAOUser;
import com.finEasy.models.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);

}
