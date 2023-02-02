/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.CartObject;
import com.loctt.app.service.ICartService;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class CartService implements ICartService {

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

}
