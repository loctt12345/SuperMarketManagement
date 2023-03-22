/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.repository;

import com.loctt.app.model.Employee;
import com.loctt.app.model.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ADMIN
 */
public interface IEmployeeRepository extends JpaRepository<Employee, String>{
    Employee findByUsername(String username);
    Employee findByEmployeeIDAndRoleRoleNameNotAndStatus(String employeeID, String roleName, boolean status);
    List<Employee> findByFullNameContainingAndRoleRoleNameNotAndStatus(String fullName, String roleName, boolean status);
    List<Employee> findByStatusAndRoleRoleNameNot(boolean status, String roleName);
    Employee findByEmployeeID(String employeeID);
 }
