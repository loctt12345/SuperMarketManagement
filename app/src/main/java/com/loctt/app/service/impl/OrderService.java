/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.OrderDetails;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.model.OrderStatus;
import com.loctt.app.repository.IOrderDetailsRepository;
import com.loctt.app.repository.IOrderStatusRepository;
import com.loctt.app.repository.IPrimaryOrderRepository;
import com.loctt.app.repository.IProductRepository;
import com.loctt.app.service.IOrderService;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class OrderService implements IOrderService{
    
    @Autowired
    IOrderStatusRepository orderStatusRepository;
    
    @Autowired
    IPrimaryOrderRepository primaryOrderRepository;
    
    @Autowired
    IOrderDetailsRepository orderDetailsRepository;
    
    
    @Autowired
    IProductRepository productRepository;
    
//    @Autowired
//    IProductService productService;
    
    @Override
    public OrderStatus findorderStatusID(int id) {
        return this.orderStatusRepository.findByorderStatusID(id);
    }

    @Override
    public void saveOrder(PrimaryOrder orderSaved) {
        
        //System.out.println(orderSaved.toString());
        primaryOrderRepository.save(orderSaved);
    }

    @Override
    public void saveOrderDetails(CartObject cart, String orderID) {
        //Get Items in Cart
        Map<String, Integer> items = cart.getItems();
        
        if (items != null) {
            //reset total
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                //Generate ID for items
                String id = GenerateUUID.getUUID();
                
                //Get sellPrice of Product
                float sellPrice = productRepository
                                  .findByProductID(entry.getKey())
                                  .getSellprice();
                
                //Amount of this Product
                float amount = sellPrice * entry.getValue();
                
                
                //Generate the OrderDetails to save Database
                OrderDetails orderItems = new OrderDetails(id, orderID, entry.getKey(), entry.getValue(), amount);
                orderDetailsRepository.save(orderItems);
                
            }
        }
    }

    @Override
    public float getTotalOfOrder(CartObject cart) {
        float total = -1;
        Map<String, Integer> items = cart.getItems();
        
        if (items != null) {
            total = 0;
            //reset total
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                
                //Get the price of this Product 
                float price = productRepository
                              .findByProductID(entry.getKey())
                              .getSellprice() * entry.getValue();
                
                //sum total
                total += price;
                
            }
        }
        return total;
    }

    @Override
    public void updateOrderStatus(String orderId, int status) {
        try{
            PrimaryOrder order = primaryOrderRepository.findById(orderId).get();
            order.setStatusID(status);
            primaryOrderRepository.save(order);
            
        
        } catch(NoSuchElementException ex) {
            System.out.println("No " + orderId + " exist!!!!");
        
        }
    }
    
    @Override
    public PrimaryOrder getPrimaryOrder(String orderId) {
        try{
            return primaryOrderRepository.findById(orderId).get();
            
        
        } catch(NoSuchElementException ex) {
            System.out.println("No " + orderId + " exist!!!!");
        
        }
        return null;
    }
    

    
}
