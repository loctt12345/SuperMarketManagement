/*/
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PrimaryOrder")
public class PrimaryOrder {
    
    @Id
    @Column(name="OrderID")
    private String orderID;
    
    @Column(name = "UserID")
    private String userID;
    
    @Column(name = "StatusID")
    private int statusID;
    
    @Column(name = "Time")
    @Temporal(TemporalType.DATE)
    private Date time;
    
    @Column(name = "Shipping_Address")
    private String shippingAddress;
    
    @Column(name = "total")
    private float total;
    
    @Column(name = "Phone")
    private String phone;
    
    @Column(name = "Email")
    private String email;

    public PrimaryOrder() {
    }

    public PrimaryOrder(String orderID, String userID, int statusID, Date time, String shippingAddress, float total, String phone, String email) {
        this.orderID = orderID;
        this.userID = userID;
        this.statusID = statusID;
        this.time = time;
        this.shippingAddress = shippingAddress;
        this.total = total;
        this.phone = phone;
        this.email = email;
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
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the statusID
     */
    public int getStatusID() {
        return statusID;
    }

    /**
     * @param statusID the statusID to set
     */
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the shippingAddress
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * @param shippingAddress the shippingAddress to set
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.orderID
        + " " + this.email + " " + this.phone + " " + this.shippingAddress + " " + this.userID + " " + this.statusID ;
                //+ " " + this.time + " " + this.total;
    }
    
    
    
    
}
