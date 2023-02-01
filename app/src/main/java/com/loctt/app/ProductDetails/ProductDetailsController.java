/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.ProductDetails;

import com.loctt.app.ProductDetails.IProductDetailsRepository;
import com.loctt.app.ProductDetails.ProductDetailsDTO;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */

@RestController
public class ProductDetailsController {
    
    //Test Map API
    @GetMapping("/testApi")
    public String TestAPI(){
        return "Hello World!!";
    }

    
    //Map ProductDetails
    @Autowired
    IProductDetailsRepository ProductDetailsRepo;
    
    //Find Product Details By ID
    @GetMapping("/api/test")
    public ProductDetailsDTO getProductByID(@RequestParam(name = "txtID", defaultValue = "0000")String productID) {
        ProductDetailsDTO productDetails = ProductDetailsRepo.findByProductID(productID);
        return productDetails;
    
    }

}