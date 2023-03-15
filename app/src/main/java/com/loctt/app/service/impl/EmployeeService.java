/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.Employee;
import com.loctt.app.repository.IEmployeeRepository;
import com.loctt.app.service.IEmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee findByEmployeeIDForSearch(String employeeID) {
        return employeeRepository.findByEmployeeIDAndRoleRoleNameNotAndStatus(employeeID,"ADMIN", true);
    }

    @Override
    public List<Employee> findByNameContainingForSearch(String fullName) {
        return employeeRepository.findByFullNameContainingAndRoleRoleNameNotAndStatus(fullName,"ADMIN", true);
    }

    @Override
    public List<Employee> findAllForSearch() {
        return employeeRepository.findByStatusAndRoleRoleNameNot(true, "ADMIN");
    }

}
