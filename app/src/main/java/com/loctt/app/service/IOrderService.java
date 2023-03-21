/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.loctt.app.service;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.OrderDetails;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.model.OrderStatus;
import com.loctt.app.model.ProductDetails;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface IOrderService {
    
    //Get orderStatus
    public OrderStatus findorderStatusID(int id);
    
    
    //Save Order to Database
    public void saveOrder(PrimaryOrder Order);
    
    public void saveOrderDetails(CartObject cart, String orderID);
    
    public float getTotalOfOrder(CartObject cart);
    public PrimaryOrder findByOrderID(String orderID);
    public List<OrderDetails> getOrderDetails(String orderID);
    public Map<ProductDetails, Integer> getListProduct(String orderID);    
    public void updateOrderStatus(String orderID, int status);
    public PrimaryOrder getPrimaryOrder(String orderId);
    public List<PrimaryOrder> findByTimedateBetween(Date timeLess, Date timeGreater);
    public float[] getTotalProfitByMonth(int month, int year);
    public float[] getTotalProfitByYear(int year);
    public float calcTotal(List<PrimaryOrder> orderList);
    public List<PrimaryOrder> getlistPrimaryOrder(String userId);
    public List<Object[]> getTotalSumProductsByMonth(int month, int year);
   
}
 