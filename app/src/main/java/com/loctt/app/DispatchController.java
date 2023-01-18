/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Administrator
 */

//FOR MAP MAIN PAGE
@Controller
public class DispatchController {
    
    @GetMapping("/")
    public String startWeb(){
        return "index";
    }
    
}
