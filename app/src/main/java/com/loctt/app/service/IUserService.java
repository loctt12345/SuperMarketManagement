/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service;

import com.loctt.app.model.User;

/**
 *
 * @author ADMIN
 */
public interface IUserService {
    public User findUserByID(String userID);
    public User findByUsername(String username);
    public User findByEmail(String email);
    public void createUser(User user);
    public long countUser();
}
