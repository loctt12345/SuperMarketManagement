/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.config;

import com.loctt.app.model.User;
import com.loctt.app.service.impl.SendMailService;
import com.loctt.app.service.impl.UserService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 *
 * @author ADMIN
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

    @Autowired
    private UserService userService;
    @Bean
    public SendMailService sendMailService(){
        return new SendMailService();
    }
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        User customer = userService.findByUsername(username);
        if(customer != null && !customer.getStatus() && pwdEncoder.matches(password, customer.getPassword())){
            String email = customer.getEmail();
            String verificationCode= RandomString.make(6);
            customer.setVerificationCode(verificationCode);
            userService.updateUser(customer);
            try {
                sendMailService().sendEmailVerification(email, verificationCode, mailSender);
            } catch (UnsupportedEncodingException | MessagingException ex) {
                response.sendRedirect("/login?errorVerify=true");
            }
            request.getSession().setAttribute("VerificationRightFlow", true);
            response.sendRedirect("/verifyMail");
        }
        else{
            response.sendRedirect("/login?error=true");
        }
    }
    
}
