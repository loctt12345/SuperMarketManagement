/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.model;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author ADMIN
 */
public class UserDetailsPrincipal implements UserDetails {

    private User user;
    private Employee employee;
    private String role;

    public Employee getEmployee() {
        return employee;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+this.role));
    }

    @Override
    public String getPassword() {
        if (this.user != null) {
            return this.user.getPassword();
        } else {
            return this.employee.getPassword();
        }
    }

    @Override
    public String getUsername() {
        if (this.user != null) {
            return this.user.getUsername();
        } else {
            return this.employee.getUsername();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetailsPrincipal(User user) {
        this.user = user;
    }

    public UserDetailsPrincipal(Employee employee) {
        this.employee = employee;
    }

}
