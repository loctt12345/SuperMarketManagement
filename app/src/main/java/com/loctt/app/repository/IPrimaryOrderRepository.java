/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.loctt.app.repository;

import com.loctt.app.model.PrimaryOrder;
import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface IPrimaryOrderRepository extends JpaRepository<PrimaryOrder, String> {
    
    List<PrimaryOrder> findAllByOrderByStatusIDAscTimeDesc();
    
    PrimaryOrder findByOrderID(String orderID);

    List<PrimaryOrder> findAllByTimeBetween(Date start, Date end);

    List<PrimaryOrder> findByUserID(String userId);

    List<PrimaryOrder> findByUserIDOrderByTimeDesc(String userId);

    @Query(value = "select d.ProductID, SUM(d.Sold_Number) as SoldNum from "
            + "Primary_Order as p join Order_Details as d "
            + "on p.OrderID LIKE d.OrderID "
            + "where MONTH(p.Time) = ?1 and YEAR(p.Time) = ?2 "
            + "Group by d.ProductID "
            + "Order by SUM(d.Sold_Number) DESC", nativeQuery = true)
    List<Object[]> getTotalSumProductsByMonth(int month, int year);

}
