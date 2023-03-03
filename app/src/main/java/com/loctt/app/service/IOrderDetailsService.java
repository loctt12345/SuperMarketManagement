/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service;

import com.loctt.app.model.OrderDetails;
import java.util.List;

/**
 *
 * @author loc12345
 */
public interface IOrderDetailsService {
    public List<OrderDetails> findOrderDetailsByOrderId(String orderId);
}
