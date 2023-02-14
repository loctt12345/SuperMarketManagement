/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.ProductManager;

/**
 *
 * @author ADMIN
 */
public interface IProductManagerService {
    
    public ProductManager findByProductID(String productID);
    public boolean isAvailable(CartObject cart);
    
    public void updateAvailableNumber();
    
}
