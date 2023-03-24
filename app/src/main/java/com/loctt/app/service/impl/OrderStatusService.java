/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.OrderStatus;
import com.loctt.app.repository.IOrderStatusRepository;
import com.loctt.app.service.IOrderStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author loc12345
 */
@Service
public class OrderStatusService implements IOrderStatusService{
    @Autowired
    IOrderStatusRepository orderStatusRepo;

    @Override
    public List<OrderStatus> findAll() {
        return orderStatusRepo.findAll();
    }
    
    @Override
    public OrderStatus findById(int id) {
        return orderStatusRepo.findByorderStatusID(id);
    }
}
