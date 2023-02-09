/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.CartObject;
import com.loctt.app.service.impl.CartService;
import com.loctt.app.service.impl.ProductService;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
//FOR MAP MAIN PAGE
@Controller
@ControllerAdvice

public class DispatchController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @ModelAttribute
    public void commonAttr(Model model, HttpSession session) {
        CartObject cart = (CartObject) session.getAttribute("CART");
        model.addAttribute("cartSize", this.cartService.getCartSize(cart));
    }

    @GetMapping("/")
    public String startWeb() {
        return "index";
    }
    //TestAdmin
    @GetMapping("/admin-page")
    public String adminPage() {
        return "products_management";
    }
    @GetMapping("/product-detail")
    public String showProduct(Model model, @RequestParam(name = "productID") String productID) {
        model.addAttribute("product_id", productID);
        return "product_detail";
    }
    //Add new Item to Cart
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam(name = "txtProductID") String txtProductID,
            @RequestParam(name = "txtNumber") String txtNumber, HttpSession session) {

        //Initial Class, Do service
        if (productService.findByProductID(txtProductID) != null)
            cartService.addToCart(txtProductID, txtNumber, session);
        return "redirect:/";
    }

    @GetMapping("/showCart")
    public String showCart(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            Model model,
            HttpSession session) {
        model.addAttribute("page", page);
        CartObject cart = (CartObject) session.getAttribute("CART");
        model.addAttribute("numPage", (int) Math.ceil((float) cartService.getCartSize(cart) / 6));
        return "cart_page";
    }

}
