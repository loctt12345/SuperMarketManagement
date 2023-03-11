/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author loc12345
 */
@Entity
@Table(name = "Product_Recommendation")
public class ProductRecommendation {
    @Id
    @Column(name = "CommentID")
    private String commentId;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "ProductID")
    private String productID;
    @Column(name = "Status")
    private boolean status;

    public ProductRecommendation() {
    }

    public ProductRecommendation(String commentId,
            String comment, String productID, boolean status) {
        this.commentId = commentId;
        this.comment = comment;
        this.productID = productID;
        this.status = status;
    }
    
    

    /**
     * @return the commentId
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * @param commentId the commentId to set
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
