/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service;

import com.loctt.app.model.ProductDetails;

/**
 *
 * @author loc12345
 */
public interface IProductService {
    public ProductDetails findByProductID(String productID);
}
