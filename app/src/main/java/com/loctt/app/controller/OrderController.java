/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.model.OrderStatus;
import com.loctt.app.service.IOrderService;
import com.loctt.app.service.impl.GenerateUUID;
import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */

@RestController
public class OrderController {
    
    @Autowired
    IOrderService orderService;
    
    @GetMapping("/test")
    public OrderStatus getOrderStatus(@RequestParam(name="txtID") String ID) {
        
        OrderStatus result = orderService.findorderStatusID(Integer.parseInt(ID));
        
        return result;
    
    }
    
    @PostMapping("/payingA") 
    public void savedOrder(@RequestParam(name="userEmail") String email, 
                           @RequestParam(name="userPhone") String phone, 
                           @RequestParam(name="userAddress") String address,
                           HttpSession session)  {
        
    
            //Get CART OBJECT
            CartObject cart = (CartObject) session.getAttribute("CART");
            //1. Add new Order
            PrimaryOrder orderSaved = new PrimaryOrder();
            
            String orderID = GenerateUUID.getUUID();
            
            //Set Shipping information
            orderSaved.setOrderID(orderID);
            orderSaved.setEmail(email);
            orderSaved.setPhone(phone);
            orderSaved.setShippingAddress(address);
            
            //Set Customer
            orderSaved.setUserID("CUS011");
            orderSaved.setStatusID(1);
            orderSaved.setTime(new Date());
            
            //Set Total For order
            float total = orderService.getTotalOfOrder(cart);
            orderSaved.setTotal(total);
            System.out.println("This is order: " + orderSaved.toString());
            orderService.saveOrder(orderSaved);
            

            //2. call Add Order Details Service 
            orderService.saveOrderDetails(cart, orderID);
        
    }
    
}
