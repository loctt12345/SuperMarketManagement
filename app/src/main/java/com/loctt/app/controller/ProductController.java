/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.loctt.app.service.IProductService;
import java.util.List;
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

    @Autowired
    private IOrderService orderService;

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

    @GetMapping("/api/topproducts")
    public ResponseEntity<List<Object>> getTopTenProducts(@RequestParam(name = "month") int month,
            @RequestParam(name = "year") int year) {
        List<Object> topProducts = orderService.getTotalSumProductsByMonth(month, year);
//        System.out.println("List topProducts xem lay dc ko hen: " + topProducts.size());
//        for (int i = 0; i < topProducts.size(); i++) {
//            try {
//                System.out.println(topProducts.get(i).getClass().getField("ProductID"));
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
        return ResponseEntity.ok().body(topProducts);
        //return ResponseEntity.ok().body("");
    }

}
