/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.controller;

import com.loctt.app.model.CartObject;
import com.loctt.app.service.impl.CartService;
import com.loctt.app.service.impl.ProductService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @PostMapping("/api/cart/update")
    public ResponseEntity updateProductInCart(@RequestBody JSONObject object, Model model, HttpSession session) {
        String productID = object.getAsString("productID");
        HashMap<String, String> response = new HashMap<>();
        int quantityInCart = Integer.parseInt(object.getAsString("quantityInCart"));
        CartObject cart = (CartObject) session.getAttribute("CART");

        if (cart != null) {
            cartService.updateItemInCart(productID, quantityInCart, cart);
            session.setAttribute("CART", cart);
            response.put("productID", productID);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/cart/remove")
    public ResponseEntity removeProductInCart(@RequestBody JSONObject object, Model model, HttpSession session) {
        String productID = object.getAsString("productID");
        HashMap<String, String> response = new HashMap<>();
        CartObject cart = (CartObject) session.getAttribute("CART");

        if (cart != null) {
            cartService.removeItemInCart(productID, cart);
            session.setAttribute("CART", cart);
            response.put("productID", productID);
            response.put("cartSize", String.valueOf(cartService.getCartSize(cart)));
        }
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/api/cart/show/{pageNum}")
    public ResponseEntity showCart(@PathVariable(value = "pageNum") Integer pageNum, Model model, HttpSession session) {
        CartObject cart = (CartObject) session.getAttribute("CART");
        int maxResult = 6;
        int fromItemIndex = (pageNum - 1) * maxResult + 1;
        int maxItemIndex = fromItemIndex + maxResult - 1;
        if (cart != null && cart.getItems() != null) {
            return ResponseEntity.ok().body(cartService.showCart(cart, fromItemIndex, maxItemIndex));
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    @GetMapping("/api/cart/getTotalPriceInCart")
    public ResponseEntity getTotalPrice(HttpSession session) {
        float result = 0;
        CartObject cart = (CartObject) session.getAttribute("CART");
        if (cart != null && cart.getItems() != null) {
            for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
                result
                        += productService.findByProductID(entry.getKey()).getSellprice()
                        * entry.getValue();
            }
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/api/cart/getTotalPriceInCartToDollar")
    public ResponseEntity getTotalPriceToDollar(HttpSession session) {
        float result = 0;
        CartObject cart = (CartObject) session.getAttribute("CART");
        if (cart != null && cart.getItems() != null) {
            for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
                result
                        += productService.findByProductID(entry.getKey()).getSellprice()
                        * entry.getValue();
            }
        }
        return ResponseEntity.ok().body(result/23000);

    }

    //Add new Item to Cart
    @GetMapping("api/cart/addToCart")
    public ResponseEntity addToCart(@RequestParam(name = "txtProductID") String txtProductID,
            @RequestParam(name = "txtNumber") String txtNumber, HttpSession session) {
        if (productService.findByProductID(txtProductID) != null) {
            cartService.addToCart(txtProductID, txtNumber, session);
        }
        return ResponseEntity.ok().body(null);
    }
}
