/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service;

import com.loctt.app.model.CartObject;
import javax.servlet.http.HttpSession;
import net.minidev.json.JSONObject;

/**
 *
 * @author ADMIN
 */
public interface ICartService {

    public void addToCart(String productID, String txtNumber, HttpSession session);

    public void updateItemInCart(String productID, int quantityInCart, CartObject cart);

    public void removeItemInCart(String productID, CartObject cart);

    public JSONObject showCart(CartObject cart, int fromItemIndex, int maxItemIndex);
    
    public int getCartSize(CartObject cart);
}
