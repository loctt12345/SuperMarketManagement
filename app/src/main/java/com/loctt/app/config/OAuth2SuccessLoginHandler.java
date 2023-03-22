/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.config;

import com.loctt.app.model.AuthenticationProvider;
import com.loctt.app.model.CustomOAuth2User;
import com.loctt.app.model.User;
import com.loctt.app.service.impl.GenerateUUID;
import com.loctt.app.service.impl.UserService;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author ADMIN
 */
@Component
public class OAuth2SuccessLoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String userEmail = oauthUser.getEmail();
        User customer = this.userService.findByUsername(userEmail);
        User userForCheckEmail = this.userService.findByEmail(userEmail);
        if (userForCheckEmail != null && 
                userForCheckEmail.getAuthenticationProvider() == null) {
            response.sendRedirect("/logout?errorEmail=true");
        }
        
        if (customer == null && userForCheckEmail == null) {
            //check valid email
            //if haven't register yet-> register
            User newCustomer = new User(oauthUser.getEmail(),
                    oauthUser.getName(), userEmail, AuthenticationProvider.GOOGLE);
            newCustomer.setStatus(true);
            userService.createNewUser(newCustomer);
            response.sendRedirect("/");
            return;
        }
        
        if (userForCheckEmail.getAuthenticationProvider() == AuthenticationProvider.GOOGLE) {
            response.sendRedirect("/");
        }
    }

}
