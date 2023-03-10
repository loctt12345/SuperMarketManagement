/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import java.util.List;
import net.minidev.json.JSONObject;
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

    @PostMapping("api/dashboard/getMonthRevenue")
    public ResponseEntity getMonthRevenue (@RequestBody JSONObject object) {
        int month = Integer.parseInt(object.getAsString("txtMonth"));

        float[] listRevenue = new float[10];
        if (month == 1) {
            listRevenue[0] = 1000;
            listRevenue[1] = 1200;
            listRevenue[2] = 1400;
            listRevenue[3] = 1500;

        } else {
            listRevenue[0] = 1500;
            listRevenue[1] = 1000;
            listRevenue[2] = 500;
            listRevenue[3] = 800;

        }

        return ResponseEntity.ok().body(listRevenue);

    }

}