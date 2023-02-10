/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Administrator
 */
//
@Entity
@Table(name = "Product_Details")
public class ProductDetails implements Serializable {
    @Id
//    @GeneratedValue
    @Column(name = "ProductID")
    private String productID;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Category")
    private String category;
    @Column(name = "Sell_Price")
    private float sellprice;
    @Column(name = "Image")
    private String imageLink;
    
    
    public ProductDetails() {
    }

    public ProductDetails(String productID, String name, String description, String category, String imageLink, float sellprice) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.sellprice = sellprice;
        this.imageLink = imageLink;
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the sellprice
     */
    public float getSellprice() {
        return sellprice;
    }

    /**
     * @param sellprice the sellprice to set
     */
    public void setSellprice(float sellprice) {
        this.sellprice = sellprice;
    }

    /**
     * @return the imageLink
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * @param imageLink the imageLink to set
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    

   
    
}
