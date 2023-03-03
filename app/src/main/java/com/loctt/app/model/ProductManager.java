/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "Product_Manager")
public class ProductManager implements Serializable{
    
   
    @Id
    @Column(name = "ProductID")
    private String productID;
    
    @Column(name = "Total_Imported_Number")
    private int totalImportedNumber;
    
    @Column(name = "Total_Sold_Number")
    private int totalSoldNumber;

    public ProductManager() {
    }

    public ProductManager(String productID, int totalImportedNumber, int totalSoldNumber) {
        this.productID = productID;
        this.totalImportedNumber = totalImportedNumber;
        this.totalSoldNumber = totalSoldNumber;
    }

  
 
    /**
     * @return the totalImportedNumber
     */
    public Integer getTotalImportedNumber() {
        return totalImportedNumber;
    }

    /**
     * @param totalImportedNumber the totalImportedNumber to set
     */
    public void setTotalImportedNumber(int totalImportedNumber) {
        this.totalImportedNumber = totalImportedNumber;
    }

    /**
     * @return the totalSoldNumber
     */
    public Integer getTotalSoldNumber() {
        return totalSoldNumber;
    }

    /**
     * @param totalSoldNumber the totalSoldNumber to set
     */
    public void setTotalSoldNumber(int totalSoldNumber) {
        this.totalSoldNumber = totalSoldNumber;
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
    
    
}
