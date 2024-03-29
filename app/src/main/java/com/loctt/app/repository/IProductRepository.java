/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.repository;

import com.loctt.app.model.ProductDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface IProductRepository extends JpaRepository<ProductDetails, String> {

    ProductDetails findByProductID(String ProductID);


    ProductDetails findByProductIDAndStatusNot(String ProductID, boolean status);

    List<ProductDetails> findByCategoryContainingAndStatusNot(String category, boolean status);

    List<ProductDetails> findByNameContainingAndStatusNot(String name, boolean status);

    ProductDetails save(ProductDetails product);

    List<ProductDetails> findByStatusNot(boolean status);
    
}
