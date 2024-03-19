package com.finEasy.models.services;

import com.finEasy.models.entity.Product;
import com.finEasy.models.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void InsertProduct(Product product){

        productRepository.save(product);
    }

}
