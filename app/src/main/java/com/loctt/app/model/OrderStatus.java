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
@Table(name = "Order_Status")
public class OrderStatus {
    @Id
    @Column(name = "StatusID")
    private int orderStatusID;
    
    @Column(name = "Status_Name")
    private String name;

    public OrderStatus() {
    }

    public OrderStatus(int orderStatusID, String name) {
        this.orderStatusID = orderStatusID;
        this.name = name;
    }

    /**
     * @return the orderStatusID
     */
    public int getOrderStatusID() {
        return orderStatusID;
    }

    /**
     * @param orderStatusID the orderStatusID to set
     */
    public void setOrderStatusID(int orderStatusID) {
        this.orderStatusID = orderStatusID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

   
    
}
