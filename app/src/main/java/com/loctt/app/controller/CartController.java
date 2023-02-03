/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.controller;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.impl.CartService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/api/cart/update")
    public ResponseEntity updateProductInCart(@RequestBody JSONObject object, Model model, HttpSession session) {
        String productID = object.getAsString("productID");
        HashMap<String, String> response = new HashMap<>();
        int quantityInCart = Integer.parseInt(object.getAsString("quantityInCart"));
        CartObject cart = (CartObject) session.getAttribute("CART");
        //test update
//        CartObject cart = new CartObject();
//        Map<String,Integer> items = new HashMap<>();
//        items.put(productID, 2);
//        cart.setItems(items);
        //
        if (cart != null) {
            cartService.updateItemInCart(productID, quantityInCart, cart);
            session.setAttribute("CART", cart);
            response.put("productID", productID);
            //test update
            Map<String, Integer> items = cart.getItems();
            if (items != null) {
                for (Map.Entry<String, Integer> entry : items.entrySet()) {
                    System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                }
            }
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/cart/remove")
    public ResponseEntity removeProductInCart(@RequestBody JSONObject object, Model model, HttpSession session) {
        String productID = object.getAsString("productID");
        HashMap<String, String> response = new HashMap<>();
        CartObject cart = (CartObject) session.getAttribute("CART");
        //test remove
//        CartObject cart = new CartObject();
//        Map<String, Integer> items = new HashMap<>();
//        items.put(productID, 2);
//        items.put("8934563138164", 2);
//        items.put("8934563138144", 2);
//        cart.setItems(items);
        //
        if (cart != null) {
            cartService.removeItemInCart(productID, cart);
            session.setAttribute("CART", cart);
            response.put("productID", productID);
            //test remove
//            Map<String, Integer> items = cart.getItems();
//            if (items != null) {
//                for (Map.Entry<String, Integer> entry : items.entrySet()) {
//                    System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//                }
//            }
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/cart/show/{pageNum}")
    public ResponseEntity showCart(@RequestBody JSONObject object, Model model, HttpSession session) {
        CartObject cart = (CartObject) session.getAttribute("CART");
        return ResponseEntity.ok().body(cartService.showCart(cart));
    }
}
