/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.repository.IPrimaryOrderRepository;
import com.loctt.app.service.IOrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
public class DashboardApiController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPrimaryOrderRepository primaryOrderRepository;

    @PostMapping("api/dashboard/getMonthRevenue")
    public ResponseEntity getMonthRevenue(@RequestBody JSONObject object) {
        int month = Integer.parseInt(object.getAsString("txtMonth"));
        int yearOfMonth = Integer.parseInt(object.getAsString("txtYearOfMonth"));
        float[] listRevenue = orderService
                .getTotalProfitByMonth(month, yearOfMonth);
        return ResponseEntity.ok().body(listRevenue);
    }
    
    @PostMapping("api/dashboard/getYearRevenue")
    public ResponseEntity getYearRevenue(@RequestBody JSONObject object) {
        int year = Integer.parseInt(object.getAsString("txtYear"));
        float[] listRevenue = orderService
                .getTotalProfitByYear(year);
  
        return ResponseEntity.ok().body(listRevenue);
    }

}
