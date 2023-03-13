/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.User;
import com.loctt.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@Controller
public class UserController {
    
    @Autowired
    IUserService userService;
    
    @PostMapping("user/update")
    public String updateProfile(@RequestParam (name = "txtUserId") String userId,
                               @RequestParam (name = "txtFullName") String fullName,
                               @RequestParam (name = "txtPhone") String phone,
                               @RequestParam (name = "txtEmail") String email,
                               @RequestParam (name = "txtAddress") String address){
        
        User userUpdated = userService.findUserByID(userId);
        if (userUpdated != null) {
            userUpdated.setFullName(fullName);
            userUpdated.setAddress(address);
            userUpdated.setEmail(email);
            userUpdated.setPhone(phone);
            
            userService.updateProfile(userUpdated);
        }
        
        return "redirect:/";
    }
    
    
}
