/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.repository;

import com.loctt.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public interface IUserRepository extends JpaRepository<User, String>{
    User findByUserIDAndStatus(String userID, boolean Status);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByResetPasswordTokenAndStatus(String token, boolean Status);
    User findByVerificationCode(String verificationCode);
}
