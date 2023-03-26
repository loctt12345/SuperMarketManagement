/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.CustomOAuth2User;
import com.loctt.app.model.Employee;
import com.loctt.app.model.User;
import com.loctt.app.model.UserDetailsPrincipal;
import com.loctt.app.service.IEmployeeService;
import com.loctt.app.service.IUserService;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    IEmployeeService employeeService;

    @GetMapping("/user/profile")
    public String showProfile(Authentication authentication,
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "errorEmail", required = false) String errorEmail) {
        String userId = "";
        if(errorEmail != null){
            model.addAttribute("ErrorEmail","Email đã tồn tại!!!");
        }
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            // if user login with Gmail
            String gmail = ((CustomOAuth2User) authentication.getPrincipal())
                    .getEmail();
            userId = userService.findByEmailAndStatusTrue(gmail).getUserID();
            model.addAttribute("USER", userService.findUserByID(userId));
        } else if (authentication.getPrincipal() instanceof UserDetailsPrincipal) {
            if (request.isUserInRole("ROLE_USER")) {
                userId = ((UserDetailsPrincipal) authentication.getPrincipal())
                        .getUser().getUserID();
                model.addAttribute("USER", userService.findUserByID(userId));
            } else {
                return "redirect:/user/show_change_password";
            }
        }

        return "edit_profile";
    }

    @GetMapping("/user/show_change_password")
    public String showChangePassword(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            return "redirect:/user/profile";
        }
        return "change_password";
    }

    @PostMapping("/user/change_password")
    public String changePassword(
            @RequestParam(name = "txtOldPassword") String oldPassword,
            @RequestParam(name = "txtNewPassword") String newPassword,
            Authentication authentication,
            Model model,
            HttpServletRequest request) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            return "redirect:/user/profile";
        } else if (authentication.getPrincipal() instanceof UserDetailsPrincipal) {
            if (request.isUserInRole("ROLE_USER")) {
                User user = (User) ((UserDetailsPrincipal) authentication.getPrincipal())
                        .getUser();
                if (encoder.matches(oldPassword, user.getPassword())) {
                    userService.updatePassword(user, newPassword);
                } else {
                    model.addAttribute("MSG", "Sai mật khẩu!!!");
                    return "change_password";
                }
            } else {
                Employee emp = (Employee) ((UserDetailsPrincipal) authentication.getPrincipal())
                        .getEmployee();
                if (encoder.matches(oldPassword, emp.getPassword())) {
                    employeeService.updatePassword(emp, newPassword);
                } else {
                    model.addAttribute("MSG", "Sai mật khẩu!!!");
                    return "change_password";
                }
            }
        }

        return "redirect:/logout";
    }

    @PostMapping("/user/update")
    public String updateProfile(
            @RequestParam(name = "txtFullName", required = false) String fullName,
            @RequestParam(name = "txtPhone") String phone,
            @RequestParam(name = "txtEmail", required = false) String email,
            @RequestParam(name = "txtAddress") String address,
            Authentication authentication) {
        String userId = "";
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            // if user login with Gmail
            String gmail = ((CustomOAuth2User) authentication.getPrincipal())
                    .getEmail();
            userId = userService.findByEmailAndStatusTrue(gmail).getUserID();
            User userUpdated = userService.findUserByID(userId);
            if (userUpdated != null) {
                userUpdated.setAddress(address);
                userUpdated.setPhone(phone);

                userService.updateProfile(userUpdated);
            }
        } else {
            userId = ((UserDetailsPrincipal) authentication.getPrincipal())
                    .getUser().getUserID();
            User userUpdated = userService.findUserByID(userId);
            if (userUpdated != null && userService.findByEmailAndStatusTrue(email) == null) {
                userUpdated.setFullName(fullName);
                userUpdated.setAddress(address);
                userUpdated.setEmail(email);
                userUpdated.setStatus(false);
                userUpdated.setPhone(phone);

                userService.updateProfile(userUpdated);
            }
            else{
                return "redirect:/user/profile?errorEmail";
            }
        }

        return "redirect:/logout";
    }

}
