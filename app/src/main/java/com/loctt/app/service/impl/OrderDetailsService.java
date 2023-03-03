/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.OrderDetails;
import com.loctt.app.repository.IOrderDetailsRepository;
import com.loctt.app.service.IOrderDetailsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author loc12345
 */
@Service
public class OrderDetailsService implements IOrderDetailsService{
    
    @Autowired
    private IOrderDetailsRepository orderDetailsRepository;
    
    @Override
    public List<OrderDetails> findOrderDetailsByOrderId(String orderId) {
        return this.orderDetailsRepository.findByOrderID(orderId);
    }
}
