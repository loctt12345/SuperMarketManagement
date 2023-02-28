/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.OrderDetails;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.model.ProductManager;
import com.loctt.app.repository.IProductManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loctt.app.service.IProductManagerService;
import com.loctt.app.service.IProductService;
import java.util.List;
import java.util.Map;

/**
 *
 * @author loc12345
 */
@Service
public class ProductManagerService implements IProductManagerService{
    @Autowired
    private IProductManagerRepository productManagerRepository;
    
    @Autowired
    private IProductService productService;
    
    
    @Override
    public ProductManager findByProductID(String productID) {
        return this.productManagerRepository.findByProductID(productID);
    }

    
   
    @Override
    public boolean isAvailable(CartObject cart) {
        boolean result = true;
        
        Map<String, Integer> items = cart.getItems();
        
        if (items != null) {
            //reset total
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
               
                ProductManager product = findByProductID(entry.getKey());
                
                
                int importedProduct = product.getTotalImportedNumber();
                int soldProduct = product.getTotalSoldNumber();
                
                if (soldProduct + entry.getValue() > importedProduct ) {
                    //Product number is not enough
                    result = false;
                    
                    //Set the Maxium Value that Product can Prodvide
                    entry.setValue(importedProduct - soldProduct);
                    soldProduct = 0;
                } 
                 
              //  System.out.println("Product: " + entry.getKey() + " Total: " + importedProduct + "  Sold: " + soldProduct + " Number in Cart: " + entry.getValue());
                ProductManager savedProduct = new ProductManager(entry.getKey(), importedProduct, entry.getValue() + soldProduct);
                productManagerRepository.save(savedProduct);
                
            }
        }
        
        return result;
    }

    @Override
    public void updateAvailableNumber() {
        List<ProductDetails> listProduct = productService.findAll();
        
        for (ProductDetails productDetails : listProduct) {
            String productID =  productDetails.getProductID();
            System.out.println(productID);
            if (productManagerRepository.findByProductID(productID) == null) {
                ProductManager newProductManager = new ProductManager(productID, 1000, 0);
               
                productManagerRepository.save(newProductManager);
            }
        }
        
        
        
    }

    
   
}
