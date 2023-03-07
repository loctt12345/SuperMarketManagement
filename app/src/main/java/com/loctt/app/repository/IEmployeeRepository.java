/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.repository;

import com.loctt.app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ADMIN
 */
public interface IEmployeeRepository extends JpaRepository<Employee, String>{
    Employee findByUsername(String username);
}
