/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */

//FOR MAP MAIN PAGE
@Controller
public class DispatchController {
    
    @GetMapping("/")
    public String startWeb(){
        return "index";
    }
    
    @GetMapping("/product-detail")
    public String showProduct(Model model, @RequestParam(name="productID") String productID) {
        model.addAttribute("product_id", productID);
        return "product_detail";
    }
    
}
