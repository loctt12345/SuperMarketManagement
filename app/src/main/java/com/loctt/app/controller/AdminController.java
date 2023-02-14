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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String findProducts(@RequestParam(required = false) Map<String, String> allParams, Model model) {
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
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchBy", searchBy);
        return "products_management";
    }

    //add new Product
    @PostMapping("/add-new-product")
    public String addNewProduct(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
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
        ProductDetails newProduct = new ProductDetails(productID, productName, productDes, productCategory, productSellPrice, productImageLink);
        newProduct.setStatus(true);
        this.productService.save(newProduct);
        redirectAttributes.addAttribute("searchBy", "id");
        redirectAttributes.addAttribute("searchValue", productID);
        return "redirect:/admin/findProduct";
    }

    @PostMapping("/update-product")
    public String updateProduct(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
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
        updateProduct.setStatus(true);
        productService.save(updateProduct);
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy.isEmpty() || lastSearchValue.isEmpty()) {
            return "redirect:/admin-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findProduct";
        }
    }

    @GetMapping("/delete-product")
    public String deleteProductById(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        String productID = allParams.get("id");
        if (this.productService.findByProductID(productID) != null) {
            this.productService.deleteById(productID);
        }
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy.isEmpty() || lastSearchValue.isEmpty()) {
            return "redirect:/admin-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findProduct";
        }
    }
}
