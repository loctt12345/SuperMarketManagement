/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.ProductRecommendation;
import com.loctt.app.service.IProductRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loctt.app.repository.IProductRecommendationRepository;
import java.util.List;

/**
 *
 * @author loc12345
 */
@Service
public class ProductRecommendationService implements IProductRecommendationService {
    @Autowired
    IProductRecommendationRepository productRecommendationRepository ;
    
    public void createNewRecommendation(ProductRecommendation productRecommendation) {
        productRecommendationRepository.save(productRecommendation);
    }
    
    public List<ProductRecommendation> getAll() {
        return productRecommendationRepository.findAll();
    }
}
