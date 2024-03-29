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
    void updateProfile(User user);

    public User findByUsername(String username);
    public void createNewUser(User user);
    public User findByEmailAndStatusTrue(String email);
    public User findByEmail(String email);
    public void createUser(User user);
    public User findMaxUserId();
    public void updateResetPassword(String token, String username);
    public User findByResetPasswordToken(String token);
    public void updatePassword(User customer, String newPassword);
    public User findByVerificationCode(String verificationCode);
    public void updateUser(User user);
}
