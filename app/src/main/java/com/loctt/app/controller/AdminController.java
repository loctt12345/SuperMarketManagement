/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.controller;

import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.impl.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    //Map ProductDetails
    @Autowired
    private ProductService productService;

    @GetMapping("/findProduct")
    public String findProducts(@RequestParam Map<String, String> allParams, Model model) {
        List<ProductDetails> productDetails = new ArrayList<>();
        String searchBy = allParams.get("searchBy");
        String searchValue = allParams.get("searchValue");
        //search All Products by Category
        if (searchBy.equalsIgnoreCase("category") && this.productService.findByCategoryContaining(searchValue) != null) {
            productDetails = this.productService.findByCategoryContaining(searchValue);
        }
        //search All Products by Name
        if (searchBy.equalsIgnoreCase("name") && this.productService.findByNameContaining(searchValue) != null) {
            productDetails = this.productService.findByNameContaining(searchValue);
        }
        //search All Products by ID
        if (searchBy.equalsIgnoreCase("id") && this.productService.findByProductID(searchValue) != null) {
            productDetails.add(this.productService.findByProductID(searchValue));
        }
        if (productDetails.size() > 0) {
            model.addAttribute("PRODUCTS_RESULT", productDetails);
        }
        model.addAttribute("lastSearchValue", searchValue);
        model.addAttribute("lastSearchBy", searchBy);
        return "products_management";
    }

    //add new Product
    @PostMapping("/add-new-product")
    public String addNewProduct(@RequestParam Map<String, String> allParams, Model model) {
        String productID = allParams.get("id");
        String productName = allParams.get("name");
        String productDes = allParams.get("description");
        String productCategory = allParams.get("category");
        String productImageLink = allParams.get("image");
        String sellPriceAsString = allParams.get("sellPrice");
        float productSellPrice = 0;
        if (sellPriceAsString.equalsIgnoreCase("")) {
            productSellPrice = productService.findByProductID(productID).getSellprice();
        } else {
            try {
                productSellPrice = Float.parseFloat(sellPriceAsString);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        ProductDetails newProduct = new ProductDetails(productID, productName, productDes, productCategory, productImageLink, productSellPrice);
        productService.save(newProduct);
        String lastSearchValue = allParams.get("lastSearchValue");
        model.addAttribute("lastSearchValue", lastSearchValue);
        String lastSearchBy = allParams.get("lastSearchBy");
        model.addAttribute("lastSearchBy", lastSearchBy);
        return "redirect:/admin/findProduct?searchValue=" + lastSearchValue + "&searchBy=" + lastSearchBy;
    }

    @PostMapping("/update-product")
    public String updateProduct(@RequestParam Map<String, String> allParams, Model model) {
        String productID = allParams.get("productID");
        String productName = allParams.get("productName");
        if (productName.equalsIgnoreCase("")) {
            productName = productService.findByProductID(productID).getName();
        }
        String sellPriceAsString = allParams.get("sellPrice");
        float sellPrice = 0;
        if (sellPriceAsString.equalsIgnoreCase("")) {
            sellPrice = productService.findByProductID(productID).getSellprice();
        } else {
            try {
                sellPrice = Float.parseFloat(sellPriceAsString);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        ProductDetails updateProduct = new ProductDetails();
        updateProduct.setProductID(productID);
        updateProduct.setName(productName);
        updateProduct.setCategory(productService.findByProductID(productID).getCategory());
        updateProduct.setDescription(productService.findByProductID(productID).getDescription());
        updateProduct.setImageLink(productService.findByProductID(productID).getImageLink());
        updateProduct.setSellprice(sellPrice);
        productService.save(updateProduct);
        String lastSearchValue = allParams.get("lastSearchValue");
        model.addAttribute("lastSearchValue", lastSearchValue);
        String lastSearchBy = allParams.get("lastSearchBy");
        model.addAttribute("lastSearchBy", lastSearchBy);
        return "redirect:/admin/findProduct?searchValue=" + lastSearchValue + "&searchBy=" + lastSearchBy;
    }

    @GetMapping("/delete-product")
    public String deleteProductById(@RequestParam Map<String, String> allParams, Model model) {
        String productID = allParams.get("id");
        if (this.productService.findByProductID(productID) != null) {
            this.productService.deleteById(productID);
        }
        String lastSearchValue = allParams.get("lastSearchValue");
        model.addAttribute("lastSearchValue", lastSearchValue);
        String lastSearchBy = allParams.get("lastSearchBy");
        model.addAttribute("lastSearchBy", lastSearchBy);
        return "redirect:/admin/findProduct?searchValue=" + lastSearchValue + "&searchBy=" + lastSearchBy;
    }
}
