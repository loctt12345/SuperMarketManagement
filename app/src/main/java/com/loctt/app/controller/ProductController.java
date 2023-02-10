/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loctt.app.model.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.loctt.app.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@RestController
public class ProductController {

    //Map ProductDetails
    @Autowired
    private IProductService productService;

    //Find Product Details By ID
    @GetMapping("/api/products")
    public ResponseEntity<ProductDetails> getProductByID(@RequestParam(name = "productID") String productID) {
        ProductDetails productDetails = this.productService.findByProductID(productID);
        return ResponseEntity.ok().body(productDetails);
    }

    
}
