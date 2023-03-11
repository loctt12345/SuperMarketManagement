/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.service.impl.OrderService;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private OrderService orderService;
    
    @PostMapping("api/dashboard/getMonthRevenue")
    public ResponseEntity getMonthRevenue (@RequestBody JSONObject object) {
        int month = Integer.parseInt(object.getAsString("txtMonth"));

        float[] listRevenue = new float[15];
        if (month == 1) {
            orderService.getTotalProfitByMonth(1, 2022);
        } else if (month == 2) {
            orderService.getTotalProfitByMonth(2, 2022);
        } else if (month == 3) {
            orderService.getTotalProfitByMonth(3, 2022);
        } else if (month == 4) {
            orderService.getTotalProfitByMonth(4, 2022);
        } else if (month == 5) {
            orderService.getTotalProfitByMonth(5, 2022);
        } else if (month == 6) {
            orderService.getTotalProfitByMonth(6, 2022);
        } else if (month == 7) {
            orderService.getTotalProfitByMonth(7, 2022);
        } else if (month == 8) {
            orderService.getTotalProfitByMonth(8, 2022);
        } else if (month == 9) {
            orderService.getTotalProfitByMonth(9, 2022);
        } else if (month == 10) {
            orderService.getTotalProfitByMonth(10, 2022);
        } else if (month == 11) {
            orderService.getTotalProfitByMonth(11, 2022);
        } else {
            orderService.getTotalProfitByMonth(12, 2022);
        }

        return ResponseEntity.ok().body(listRevenue);

    }

}