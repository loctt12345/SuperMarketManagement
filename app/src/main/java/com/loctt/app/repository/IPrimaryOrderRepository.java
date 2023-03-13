/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.loctt.app.repository;

import com.loctt.app.model.PrimaryOrder;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface IPrimaryOrderRepository extends JpaRepository<PrimaryOrder, String>{
    PrimaryOrder findByOrderID(String orderID);
    List<PrimaryOrder> findAllByTimeBetween(Date start, Date end);
}
