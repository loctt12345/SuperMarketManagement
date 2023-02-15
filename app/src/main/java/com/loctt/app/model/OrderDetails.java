/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
    //Cơ chế của Spring Boot - Viết mà Kiểu con Rắn ThisSnake -> Generate: this_snake, 
    //Viết Chữ Hoa thì sẽ ko bị biến thành dấu _
    @Id
    @Column(name = "OrderDetailsID")
    private String orderDetailsId;
    
    @Column(name = "OrderID")
    private String orderID;
    
    @Column(name = "ProductID")
    private String productID;
    
    @Column(name = "Sold_Number")
    
    private int soldNumber;
    @Column(name = "Amount")
    private float amount;

    public OrderDetails() {
    }

    public OrderDetails(String orderDetailsID, String orderID, String productID, int soldNumber, float amount) {
        this.orderDetailsId = orderDetailsID;
        this.orderID = orderID;
        this.productID = productID;
        this.soldNumber = soldNumber;
        this.amount = amount;
    }


    /**
     * @return the orderDetailsID
     */
    public String getOrderDetailsID() {
        return getOrderDetailsId();
    }

    /**
     * @param orderDetailsID the orderDetailsID to set
     */
    public void setOrderDetailsID(String orderDetailsID) {
        this.setOrderDetailsId(orderDetailsID);
    }

    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the soldNumber
     */
    public int getSoldNumber() {
        return soldNumber;
    }

    /**
     * @param soldNumber the soldNumber to set
     */
    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }

    /**
     * @return the orderDetailsId
     */
    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    /**
     * @param orderDetailsId the orderDetailsId to set
     */
    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    /**
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    
    
}
