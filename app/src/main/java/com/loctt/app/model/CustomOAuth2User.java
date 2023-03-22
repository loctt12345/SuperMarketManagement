/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.model;

import com.loctt.app.service.IUserService;
import com.loctt.app.service.impl.UserService;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 *
 * @author ADMIN
 */
public class CustomOAuth2User implements OAuth2User{
    @Autowired
    private OAuth2User oAuth2User;
    @Autowired
    private IUserService userService;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }
    public String getFullName(){
        return getName();
    }
    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }
}
