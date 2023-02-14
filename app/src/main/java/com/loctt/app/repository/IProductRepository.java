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
public interface IProductRepository extends JpaRepository<ProductDetails, String>{
    ProductDetails findByProductID(String ProductID);
    List<ProductDetails> findByCategoryContaining(String category);
    List<ProductDetails> findByNameContaining(String name);
    //List<ProductDetails> findAll();
    ProductDetails save(ProductDetails product);
}
