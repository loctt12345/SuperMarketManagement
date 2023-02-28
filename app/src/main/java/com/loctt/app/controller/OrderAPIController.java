/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class OrderAPIController {
    
    @Autowired
    IOrderService orderService;
    
    @PostMapping("/order/updateStatus") 
    public void updateOrderStatus(@RequestParam(name="txtOrderId") String orderId, 
                           @RequestParam(name="txtStatus") int status) {
    
        orderService.updateOrderStatus(orderId, status);
    
    }
    
    @PostMapping("/order/getPrimaryOrder") 
    public PrimaryOrder getPrimaryOrder(@RequestParam(name="txtOrderId") String orderId) {
    
        return  orderService.getPrimaryOrder(orderId);
    
    }
}
