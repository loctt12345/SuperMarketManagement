/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.User;
import com.loctt.app.repository.IUserRepository;
import com.loctt.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User findUserByID(String userID) {
        return userRepository.findByUserID(userID);
    }

    @Override
    public void updateProfile(User user) {
        userRepository.save(user);
    }

}
