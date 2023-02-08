/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loctt.app.repository.IProductRepository;
import java.util.List;

/**
 *
 * @author loc12345
 */
@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;
    
    @Override
    public ProductDetails findByProductID(String productID) {
        return  this.productRepository.findByProductID(productID);
    }

    @Override
    public List<ProductDetails> findByCategoryContaining(String category) {
        return this.productRepository.findByCategoryContaining(category);
    }

    @Override
    public List<ProductDetails> findByNameContaining(String name) {
        return this.productRepository.findByNameContaining(name);
    }

    @Override
    public ProductDetails save(ProductDetails product) {
        return this.productRepository.save(product);
    }

    @Override
    public void deleteById(String productID) {
        this.productRepository.deleteById(productID);
    }
}
