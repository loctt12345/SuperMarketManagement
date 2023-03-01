/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

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
//    @GetMapping("/admin/showProductsResult/page{pageNum}")
//    public ResponseEntity showProduct(@PathVariable(value = "pageNum") Integer pageNum, Model model, HttpSession session) {
//        int maxResult = 6;
//        int fromItemIndex = (pageNum - 1) * maxResult + 1;
//        int maxItemIndex = fromItemIndex + maxResult - 1;
//        List<ProductDetails> wholeResult = (List<ProductDetails>) session.getAttribute("PRODUCTS_RESULT");
//        JSONObject response = new JSONObject();
//        if(wholeResult != null && !wholeResult.isEmpty()){
//            model.addAttribute("numPage", wholeResult.size()/6 +1);
//            return ResponseEntity.ok().body(response);
//        }else{
//            return ResponseEntity.ok().body(null);
//        }
//    }
    
}
