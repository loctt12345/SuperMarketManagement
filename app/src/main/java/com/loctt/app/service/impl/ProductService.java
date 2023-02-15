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
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ProductDetails findByProductID(String productID) {
        return this.productRepository.findByProductIDAndStatusNot(productID, false);
    }

    @Override
    public List<ProductDetails> findByCategoryContaining(String category) {
        return this.productRepository.findByCategoryContainingAndStatusNot(category, false);
    }

    @Override
    public List<ProductDetails> findByNameContaining(String name) {
        return this.productRepository.findByNameContainingAndStatusNot(name, false);
    }

    @Override
    public ProductDetails save(ProductDetails product) {
        return this.productRepository.save(product);
    }

    @Override
    public void deleteById(String productID) {
        ProductDetails product = this.productRepository.findByProductIDAndStatusNot(productID, false);
        product.setStatus(false);
        this.productRepository.save(product);
    }

    @Override
    public List<ProductDetails> findAll() {
        return this.productRepository.findByStatusNot(false);
    }

    @Override
    public List<ProductDetails> findAll() {
        return productRepository.findAll();
    }
}
