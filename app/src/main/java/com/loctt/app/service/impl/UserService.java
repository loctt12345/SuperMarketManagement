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
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return userRepository.findByUserIDAndStatus(userID, true);
    }

    @Override
    public void updateProfile(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void createNewUser(User user) {
        if (userRepository.findByUserIDAndStatus(user.getUserID(), true) == null) {
            userRepository.save(user);
        }
    }
    
    @Override
    public User findByEmailAndStatusTrue(String email) {
        return userRepository.findByEmailAndStatus(email, true);
    }
    
    @Override
    public void createUser(User user) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    @Override
    public User findMaxUserId() {
        return userRepository.findAll(Sort.by("UserId")).get(0);
    }

    @Override
    public void updateResetPassword(String token, String username) throws IllegalArgumentException{
        User customer = userRepository.findByUsername(username);
        if(customer != null && customer.getStatus()){
            customer.setResetPasswordToken(token);
            userRepository.save(customer);
        }
        else{
            throw new IllegalArgumentException("Không thể tìm thấy bất kỳ khách hàng nào có tên người dùng: " + username);
        }
    }

    @Override
    public User findByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordTokenAndStatus(token, true);
    }

    @Override
    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String encodedPassword = pwdEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);
        userRepository.save(customer);
    }

    @Override
    public User findByVerificationCode(String verificationCode) {
        return userRepository.findByVerificationCode(verificationCode);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
