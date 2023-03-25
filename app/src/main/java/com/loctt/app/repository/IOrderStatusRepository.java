/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.loctt.app.repository;

import com.loctt.app.model.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatus, Integer>{
    OrderStatus findByorderStatusID(int id);
    List<OrderStatus> findAll();
}
