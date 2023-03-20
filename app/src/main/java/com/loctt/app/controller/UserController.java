/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.CustomOAuth2User;
import com.loctt.app.model.User;
import com.loctt.app.model.UserDetailsPrincipal;
import com.loctt.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/user/profile")
    public String showProfile(Authentication authentication, Model model) {
        String userId = "";
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                    // if user login with Gmail
                    String gmail = ((CustomOAuth2User)authentication.getPrincipal())
                            .getEmail();
                    userId = userService.findByEmail(gmail).getUserID();
                }
                else {
                     userId = ((UserDetailsPrincipal) authentication.getPrincipal())
                            .getUser().getUserID();
                }
        model.addAttribute("USER", userService.findUserByID(userId));
        return "edit_profile";
    }
    
    @GetMapping("/user/show_change_password")
    public String showChangePassword() {
        return "change_password";
    }
    
    @PostMapping("/user/change_password")
    public String changePassword(
            @RequestParam (name = "txtOldPassword") String oldPassword,
            @RequestParam (name = "txtNewPassword") String newPassword,
            Authentication authentication, 
            Model model) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user= ((UserDetailsPrincipal) authentication.getPrincipal())
                            .getUser();
        if (encoder.matches(oldPassword, user.getPassword())) {
            System.out.println(newPassword);
            userService.updatePassword(user, newPassword);
        }
        else {
            model.addAttribute("MSG", "Wrong password!!!");
        }
        return "redirect:/logout";
    }
    
    @PostMapping("/user/update")
    public String updateProfile(
                               @RequestParam (name = "txtFullName") String fullName,
                               @RequestParam (name = "txtPhone") String phone,
                               @RequestParam (name = "txtEmail") String email,
                               @RequestParam (name = "txtAddress") String address,
                               Authentication authentication){
        String userId = "";
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                    // if user login with Gmail
                    String gmail = ((CustomOAuth2User)authentication.getPrincipal())
                            .getEmail();
                    userId = userService.findByEmail(gmail).getUserID();
                }
                else {
                     userId = ((UserDetailsPrincipal) authentication.getPrincipal())
                            .getUser().getUserID();
                }
        User userUpdated = userService.findUserByID(userId);
        if (userUpdated != null) {
            userUpdated.setFullName(fullName);
            userUpdated.setAddress(address);
            userUpdated.setEmail(email);
            userUpdated.setPhone(phone);
            
            userService.updateProfile(userUpdated);
        }
        
        return "redirect:/user/profile";
    }
   
}
