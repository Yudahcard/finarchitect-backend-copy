package com.finEasy.models.repository;


import com.finEasy.models.entity.Product;
import com.finEasy.models.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



}
