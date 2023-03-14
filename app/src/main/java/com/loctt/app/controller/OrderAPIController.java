/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.service.IOrderService;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @PostMapping("api/order/updateStatus") 
    public ResponseEntity updateOrderStatus(@RequestBody JSONObject object) {
        String orderId = object.getAsString("txtOrderId");
        int status = Integer.parseInt(object.getAsString("txtStatus"));
        PrimaryOrder order = orderService.findByOrderID(orderId);
        if (order.getStatusID() != (status - 1)) { 
            return ResponseEntity.badRequest().body(null);
        }
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().body(null);
    }
    
    @PostMapping("api/order/get") 
    public PrimaryOrder getPrimaryOrder(@RequestBody JSONObject object) {
        String orderId = object.getAsString("txtOrderId");
        return  orderService.getPrimaryOrder(orderId);
    }
    

    
}
