/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * 
 * @author ADMIN
 */
@Component
public class CartObject implements Serializable{

    private Map<String, Integer> items;

    public CartObject() {
    }

    public CartObject(Map<String, Integer> items) {
        this.items = items;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
    public void addItemToCart(String productID, int quantity) {
        if (productID == null) {
            return;
        
        }
        
        if (productID.trim().isEmpty()) {
            return;
        }
        
        if (quantity == 0) {
            return;
        }
        
        //Kiem tra gio ton tai chua
        if (this.items == null) {
            this.items = new HashMap<>();
        } // items have not existed
        
        //Gio ton tai thi tang so luong ngoai gio
        if (this.items.containsKey(productID)) {
            quantity += this.items.get(productID);
        } //end item has existed
        
        //Do tang ngoai gio cho nen update items
        this.items.put(productID, quantity);
        
    }
    public void updateItem(String productID, int quantityInCart) {
        if(productID == null){
            return;
        }
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(productID)) {
            this.items.put(productID, quantityInCart);
        }
    }
    public void removeItem(String productID) {
         if(productID == null){
            return;
        }
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(productID)) {
            this.items.remove(productID);
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
    }
}
