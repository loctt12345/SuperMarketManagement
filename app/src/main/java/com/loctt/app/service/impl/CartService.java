/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.ICartService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class CartService implements ICartService {

    @Autowired
    private CartObject cart;
    @Autowired
    private ProductService productService;

    @Override
    public void addToCart(String productID, String txtNumber, HttpSession session) {

        //1. Cust takes his/her cart
        CartObject cart = (CartObject) session.getAttribute("CART");
        if (cart == null) {
            cart = new CartObject();
        }
        //Change String TO Integer - number, DROP ITEM TO CART

        try {
            //4. Cust drops item down
            int number = Integer.parseInt(txtNumber.trim());
            cart.addItemToCart(productID, number);
            session.setAttribute("CART", cart);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void updateItemInCart(String productID, int quantityInCart, CartObject cart) {
        cart.updateItem(productID, quantityInCart);
    }

    @Override
    public void removeItemInCart(String productID, CartObject cart) {
        cart.removeItem(productID);
    }

    @Override
    public JSONObject showCart(CartObject cart, int fromItemIndex, int maxItemIndex) {
//        Map<String, ProductDetails> result = new HashMap<>();

        JSONObject result = new JSONObject();
        int count = 0;
        for (String productID : cart.getItems().keySet()) {
            count++;
            if(count >= fromItemIndex && count <= maxItemIndex){
            ProductDetails item = productService.findByProductID(productID);
            JSONObject element = new JSONObject();
            element.put("productID", item.getProductID());
            element.put("name", item.getName());
            element.put("description", item.getDescription());
            element.put("category", item.getCategory());
            element.put("sellprice", item.getSellprice());
            element.put("imageLink", item.getImageLink());
            element.put("quantityInCart", cart.getItems().get(productID));
            result.put(Integer.toString(count), element);
            }
        }
        //JSONObject json = new JSONObject(result);
        //json.appendField("quantityInCart", cart.getItems().get)
        System.out.println(result);
        return result;
    }
    
    @Override
    public int getCartSize(CartObject cart) {
        if (cart == null || cart.getItems() == null) return 0;
        return cart.getItems().size();
    }

}
